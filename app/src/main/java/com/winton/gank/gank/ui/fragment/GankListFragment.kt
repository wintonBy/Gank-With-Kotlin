package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.GankListAdapter
import com.winton.gank.gank.databinding.FragListCommonBinding
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.viewmodel.GankListViewModel
import com.winton.gank.gank.widget.CommItemDecoration

/**
 * @author: winton
 * @time: 2018/10/23 上午9:37
 * @desc: gank 列表
 */
class GankListFragment:BaseFragment<FragListCommonBinding>() {

    private var category:String? = null
    private var pageIndex = 1
    private lateinit var adapter:GankListAdapter
    private lateinit var viewModel: GankListViewModel

    companion object {
        const val CATEGORY = "category"
        fun newInstance(params: Bundle?):GankListFragment{
            val frag = GankListFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_list_common
    }

    override fun initView() {
        super.initView()
        binding.rvIndex.addItemDecoration(CommItemDecoration.createVertical(context!!, App.INSTANCE.resources.getColor(R.color.divider_line),2))
        binding.rvIndex.layoutManager = LinearLayoutManager(context!!)
    }

    override fun initData() {
        super.initData()
        adapter = GankListAdapter(context!!)
        binding.rvIndex.adapter = adapter
        category = arguments?.getString(CATEGORY)
        viewModel = ViewModelProviders.of(this).get(GankListViewModel::class.java)
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    binding.status.showLoading()
                }
                Resource.SUCCESS->{
                    binding.status.showContent()
                    it.data?.results?.run {
                        adapter.update(this)
                    }
                }
                Resource.ERROR->{
                    binding.status.showError()
                }
            }
        })
        category?.let {
            binding.status.showLoading()
            viewModel.loadData(it,pageIndex)
        }
    }
}