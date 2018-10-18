package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.databinding.FragIndexBinding
import com.winton.gank.gank.http.response.gank.ResultBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.viewmodel.IndexViewModel
import com.winton.gank.gank.widget.CommItemDecoration

/**
 * @author: winton
 * @time: 2018/10/9 下午7:47
 * @desc: 首页
 */
class IndexFragment: BaseFragment<FragIndexBinding>() {

    companion object {
        fun newInstance(params: Bundle?):IndexFragment{
            var frag = IndexFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    private lateinit var viewModel:IndexViewModel
    private lateinit var adapter: IndexAdapter


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

    override fun initView() {
        super.initView()
        binding.rvIndex.layoutManager = LinearLayoutManager(context)
        binding.rvIndex.addItemDecoration(CommItemDecoration.createVertical(context!!,Color.GRAY,2))
    }


    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        adapter = IndexAdapter(context!!)
        binding.rvIndex.adapter = adapter
        viewModel.getIndexData().observe(this, Observer {
            it?.let {
                when(it.status){
                    Resource.ERROR ->{
                        binding.status.showError()
                    }
                    Resource.SUCCESS ->{
                        submitTab(it.data?.category)
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

    private fun submitResult(results: ResultBean?) {
        results?.let {
            adapter.updateData(it)
        }
    }

    private fun submitTab(tabs:List<String>?){
        tabs?.let {
            binding.tabIndex.addTab(binding.tabIndex.newTab().setText("今日推荐"))
            for(item in it){
                binding.tabIndex.addTab(binding.tabIndex.newTab().setText(item))
            }
        }
    }
}