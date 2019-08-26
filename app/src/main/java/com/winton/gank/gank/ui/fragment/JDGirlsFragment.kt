package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blankj.utilcode.util.SnackbarUtils
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.JDGirlsAdapter
import com.winton.gank.gank.databinding.FragGirlsBinding
import com.winton.gank.gank.http.api.JDApi
import com.winton.gank.gank.http.response.Comment
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.viewmodel.JDViewModel

/**
 * @author: winton
 * @time: 2018/10/31 2:12 PM
 * @desc:
 */
class JDGirlsFragment:BaseFragment<FragGirlsBinding>() {

    private lateinit var viewModel:JDViewModel
    private var pageIndex:Int = 1
    private var isDoRefresh = false
    private var hasNext = true
    private lateinit var adapter:JDGirlsAdapter
    private val spanCount = 2

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
            isDoRefresh = true
            viewModel.loadData(JDApi.TYPE_GIRL,pageIndex)
        }
        binding.rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            var lastVisibleItemPos = 0
            var firstVisibleItemPos = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && hasNext){
                    lastVisibleItemPos = (recyclerView.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
                    firstVisibleItemPos = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    if(firstVisibleItemPos != 0 && lastVisibleItemPos >= adapter.itemCount - spanCount * 2 ){
                        loadMore()
                    }
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        adapter = JDGirlsAdapter(context!!)
        adapter.setOnItemClickListener{
            ImageActivity.start(activity!!, ArrayList(it.pics))
        }
        binding.rv.layoutManager = GridLayoutManager(context!!,spanCount)
        binding.rv.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(JDViewModel::class.java)
        viewModel.getData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING -> {
                    if(pageIndex == 1 && !isDoRefresh){
                        binding.sv.showLoading()
                    }
                }
                Resource.ERROR ->{
                    binding.srl.isRefreshing = false
                    if(pageIndex == 1){
                        binding.sv.showError()
                    }

                }
                Resource.SUCCESS ->{
                    binding.sv.showContent()
                    binding.srl.isRefreshing = false
                    if(pageIndex == 1){
                        it.data?.comments?.let { adapter.update(ArrayList(it)) }
                    }else{
                        it.data?.comments?.let { adapter.add(ArrayList(it)) }
                    }
                    if(it.data?.current_page == it.data?.page_count){
                        hasNext = false
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