package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
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
    private lateinit var adapter: IndexAdapter


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

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
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        adapter = IndexAdapter(context!!)
        adapter.registerOnItemClickListener(object :IndexAdapter.OnItemClick{
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