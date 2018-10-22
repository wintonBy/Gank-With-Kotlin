package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.IndexVPAdapter
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.databinding.FragIndexBinding
import com.winton.gank.gank.http.response.gank.ResultBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.ui.activity.WebActivity
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
    private lateinit var adapter: IndexVPAdapter


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

    override fun initView() {
        super.initView()
    }


    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        binding.rvIndex.adapter = adapter
        viewModel.start()
    }


    private fun submitTab(tabs:List<String>?){
        tabs?.let {
            binding.tabIndex.addTab(binding.tabIndex.newTab().setText("今日推荐"))
            if(it.contains("Android")){
                binding.tabIndex.addTab(binding.tabIndex.newTab().setText("Android"))
            }
            if(it.contains("iOS")){
                binding.tabIndex.addTab(binding.tabIndex.newTab().setText("iOS"))
            }
            if(it.contains("App")){
                binding.tabIndex.addTab(binding.tabIndex.newTab().setText("App"))
            }
            if(it.contains("福利")){
                binding.tabIndex.addTab(binding.tabIndex.newTab().setText("福利"))
            }
        }
    }
}