package com.winton.gank.gank.ui.fragment

import android.annotation.TargetApi
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Scroller
import com.blankj.utilcode.util.ToastUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.mulitype.GankListAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.databinding.FragListCommonBinding
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.WebActivity
import com.winton.gank.gank.utils.UiTools
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
    private var hasNext = true
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
        binding.rvIndex.layoutManager = LinearLayoutManager(context!!)
        binding.srl.setOnRefreshListener {
            pageIndex = 1
            category?.let {
                viewModel.loadData(it,pageIndex)
            }
        }
        UiTools.initSwipRefresh(binding.srl)
    }

    @TargetApi(23)
    override fun initListener() {
        super.initListener()
        binding.rvIndex.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            var lastVisibleItem = 0
            var isEnd = false
            var firstVisibleItem = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem != 0 && !isEnd && hasNext){
                    if(lastVisibleItem >= adapter.itemCount -3){
                        loadMore()
                    }
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(recyclerView.layoutManager is LinearLayoutManager ){
                    (recyclerView.layoutManager as LinearLayoutManager).let {
                        firstVisibleItem = it.findFirstCompletelyVisibleItemPosition()
                        lastVisibleItem = it.findLastCompletelyVisibleItemPosition()
                        isEnd = firstVisibleItem == 0
                    }
                }
            }
        })

    }

    /**
     * 初始化数据
     */
    override fun initData() {
        super.initData()
        adapter = GankListAdapter(context!!)
        adapter.setItemClickListener(object :GankListAdapter.OnItemClick{
            override fun onItemClick(item: IndexItem) {
                val url = item.item?.url
                if(url != null){
                    WebActivity.start(context!!,url)
                }else{
                    ToastUtils.showLong("链接为空")
                }
            }
        })
        binding.rvIndex.adapter = adapter
        category = arguments?.getString(CATEGORY)
        viewModel = ViewModelProviders.of(this).get(GankListViewModel::class.java)
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    if(pageIndex == 1){
                        binding.status.showLoading()
                    }
                }
                Resource.SUCCESS->{
                    binding.srl.isRefreshing = false
                    binding.status.showContent()
                    it.data?.results?.run {
                        if(pageIndex >1){
                            adapter.add(this)
                        }else{
                            adapter.update(this)
                        }
                        //默认每页加载15条数据
                        if(this.size < 15){
                            hasNext = false
                        }
                    }
                }
                Resource.ERROR->{
                    binding.srl.isRefreshing = false
                    if(pageIndex == 1){
                        binding.status.showError()
                    }else{
                        ToastUtils.showShort("加载出错")
                    }
                }
            }
        })
        category?.let {
            binding.status.showLoading()
            viewModel.loadData(it,pageIndex)
        }
    }

    /**
     * 加载更多
     */
    private fun loadMore(){
        pageIndex++
        category?.let {
            viewModel.loadData(it,pageIndex)
        }
    }
}