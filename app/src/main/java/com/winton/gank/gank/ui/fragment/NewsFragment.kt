package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.NewsAdapter
import com.winton.gank.gank.databinding.FragNewsBinding
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.viewmodel.NewsViewModel

/**
 * @author: winton
 * @time: 2018/10/9 下午7:47
 * @desc: 新闻页
 */
class NewsFragment: BaseFragment<FragNewsBinding>() {
    companion object {
        fun newInstance(params: Bundle?):NewsFragment{
            var frag = NewsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }

    }

    private lateinit var viewModel:NewsViewModel
    private lateinit var adapter:NewsAdapter

    override fun getLayoutId(): Int {
        return R.layout.frag_news
    }

    override fun initView() {
        super.initView()
        binding.rv.layoutManager = LinearLayoutManager(context!!)
    }

    override fun initData() {
        super.initData()
        adapter = NewsAdapter(context!!)
        binding.rv.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        viewModel.getList().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{

                }
                Resource.SUCCESS ->{
                    it.data?.data?.let {
                        adapter.refresh(it)
                    }
                }
                Resource.ERROR ->{

                }
            }
        })
        viewModel.loadData()
    }
}