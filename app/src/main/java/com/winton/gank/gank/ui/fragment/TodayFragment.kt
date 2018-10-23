package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.databinding.FragListCommonBinding
import com.winton.gank.gank.http.response.gank.ResultBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.ui.activity.WebActivity
import com.winton.gank.gank.viewmodel.TodayViewModel
import com.winton.gank.gank.widget.CommItemDecoration

/**
 * @author: winton
 * @time: 2018/10/22 下午8:51
 * @desc: 描述
 */
class TodayFragment:BaseFragment<FragListCommonBinding>() {

    companion object {
        fun newInstance(params: Bundle?):TodayFragment{
            var frag = TodayFragment()
            params?.apply { frag.arguments = this }
            return frag
        }

    }

    private lateinit var viewModel: TodayViewModel
    private lateinit var adapter: IndexAdapter

    override fun initView() {
        super.initView()
        binding.rvIndex.layoutManager = LinearLayoutManager(context)
        binding.rvIndex.addItemDecoration(CommItemDecoration.createVertical(context!!, App.INSTANCE.resources.getColor(R.color.divider_line),2))
        binding.srl.setOnRefreshListener {
            viewModel.start()
        }
    }

    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(TodayViewModel::class.java)
        adapter = IndexAdapter(context!!)
        adapter.registerOnItemClickListener(object : IndexAdapter.OnItemClick{
            override fun onItemClick(item: IndexItem) {
                when(item.getType()){
                    IndexAdapter.T_IMAGE ->{
                        val url = item.item?.url
                        if(url != null){
                            ImageActivity.start(context!!,url)
                        }else{
                            ToastUtils.showLong("连接为空")
                        }
                    }
                    else->{
                        var url = item.item?.url
                        if(url != null){
                            WebActivity.start(context!!,url)
                        }else{
                            ToastUtils.showLong("连接为空")
                        }
                    }
                }
            }
        })
        binding.rvIndex.adapter = adapter
        viewModel.getTodayData().observe(this, Observer {
            it?.let {
                when(it.status){
                    Resource.ERROR ->{
                        binding.status.showError()
                    }
                    Resource.SUCCESS ->{
                        submitResult(it.data?.results)
                        binding.status.showContent()
                    }
                    Resource.LOADING ->{
                        binding.status.showLoading()
                    }
                }
            }
        })
        viewModel.start()
    }
    override fun getLayoutId(): Int {
        return R.layout.frag_list_common
    }

    private fun submitResult(results: ResultBean?) {
        results?.let {
            adapter.updateData(it)
        }
    }
}