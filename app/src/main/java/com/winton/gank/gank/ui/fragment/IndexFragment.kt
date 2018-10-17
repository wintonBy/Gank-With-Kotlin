package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.databinding.FragIndexBinding
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.viewmodel.IndexViewModel

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


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

    override fun initView() {
        super.initView()
        binding.rvIndex.layoutManager = LinearLayoutManager(context)
    }


    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        viewModel.start()

        viewModel.getIndexData().observe(this, Observer {
            it?.let {
                when(it.status){
                    Resource.ERROR ->{

                    }
                    Resource.SUCCESS ->{
                        submitTab(it.data?.category)

                    }
                    Resource.LOADING ->{

                    }
                }
            }
        })
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