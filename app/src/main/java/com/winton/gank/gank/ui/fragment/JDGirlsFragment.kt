package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.JDGirlsAdapter
import com.winton.gank.gank.databinding.FragGirlsBinding
import com.winton.gank.gank.http.api.JDApi
import com.winton.gank.gank.http.response.Comment
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.viewmodel.JDViewModel

/**
 * @author: winton
 * @time: 2018/10/31 2:12 PM
 * @desc:
 */
class JDGirlsFragment:BaseFragment<FragGirlsBinding>() {

    private lateinit var viewModel:JDViewModel
    private var pageIndex:Int = 1
    private lateinit var adapter:JDGirlsAdapter

    companion object {
        fun newInstance(params: Bundle?):JDGirlsFragment{
            var frag = JDGirlsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_girls
    }

    override fun initListener() {
        super.initListener()
        binding.srl.setOnRefreshListener {
            pageIndex = 1
            viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
        }
    }

    override fun initData() {
        super.initData()
        adapter = JDGirlsAdapter(context!!)
        adapter.setOnItemClickListener(object :JDGirlsAdapter.OnItemClick{
            override fun onItemClick(item: Comment) {

            }
        })
        binding.rv.layoutManager = GridLayoutManager(context!!,2)
        binding.rv.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(JDViewModel::class.java)
        viewModel.getData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING -> {
                    if(pageIndex == 1){
                        binding.sv.showLoading()
                    }
                }
                Resource.ERROR ->{
                    if(pageIndex == 1){
                        binding.sv.showError()
                    }

                }
                Resource.SUCCESS ->{
                    binding.sv.showContent()
                    if(pageIndex == 1){
                        it.data?.comments?.let { adapter.update(ArrayList(it)) }
                    }else{
                        it.data?.comments?.let { adapter.add(ArrayList(it)) }
                    }
                }
            }
        })
        viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
    }

    private fun loadMore(){
        pageIndex++
        viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
    }
}