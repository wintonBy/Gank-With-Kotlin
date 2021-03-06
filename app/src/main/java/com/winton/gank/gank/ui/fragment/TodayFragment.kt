package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.databinding.FragListCommonBinding
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.ui.activity.WebActivity
import com.winton.gank.gank.utils.UiTools
import com.winton.gank.gank.viewmodel.TodayViewModel
import com.winton.librarystatue.IStatueListener
import kotlin.collections.ArrayList

/**
 * @author: winton
 * @time: 2018/10/22 下午8:51
 * @desc: 描述
 */
class TodayFragment:BaseFragment<FragListCommonBinding>() {

    companion object {
        fun newInstance(params: Bundle?) = TodayFragment().apply { arguments = params}
    }

    private var isFirstLoad = true
    private lateinit var viewModel : TodayViewModel
    private lateinit var adapter : IndexAdapter

    override fun initView() = with(binding) {
        rvIndex.layoutManager = LinearLayoutManager(context)
        UiTools.initSwipRefresh(srl)
        srl.setOnRefreshListener { viewModel.start() }
        status.mRetryListener = object : IStatueListener {
            override fun onRetry() {
                binding.status.showLoading()
                viewModel.start()
            }
        }
    }

    override fun initData() {
        viewModel = ViewModelProviders.of(this).get(TodayViewModel::class.java)
        adapter = IndexAdapter(context!!).apply {
            registerOnItemClickListener { item ->
                when(item.getType()) {
                    IndexAdapter.T_IMAGE -> item.item ?. url ?. let { value ->
                             ImageActivity.start(context!!, ArrayList<String>().apply { add(value) })
                        } ?: ToastUtils.showLong("链接异常")
                    else->
                        item.item ?. url ?. let { value ->
                             WebActivity.start(context!!,value)
                        } ?: ToastUtils.showLong("链接异常")
                }
            }
        }
        binding.rvIndex.adapter = adapter

        viewModel.todayDataModel.observe(this, Observer {
            when(it?.status) {
                Resource.ERROR   -> binding.status.showError()
                Resource.SUCCESS -> {
                    submitResult(it.data!!.data!!)
                    binding.status.showContent()
                    binding.srl.isRefreshing = false
                }
                Resource.LOADING ->
                    if (isFirstLoad) {
                        binding.status.showLoading()
                        isFirstLoad = false
                    }
            }
        })

        viewModel.start()
    }
    override fun getLayoutId() = R.layout.frag_list_common

    private fun submitResult(items: ArrayList<IndexItem>) = adapter.updateData(items)

}