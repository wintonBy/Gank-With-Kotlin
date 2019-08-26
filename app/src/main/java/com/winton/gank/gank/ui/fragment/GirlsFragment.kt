package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.GirlsAdapter
import com.winton.gank.gank.databinding.FragGirlsBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.utils.UiTools
import com.winton.gank.gank.viewmodel.GankListViewModel
import java.util.*

/**
 * @author: winton
 * @time: 2018/10/9 下午7:48
 * @desc: girl fragment
 */
class GirlsFragment: BaseFragment<FragGirlsBinding>() {

    private var category:String? = null
    private var pageIndex = 1
    private var hasNext = true
    private val spanCount = 2
    private var showTitle = false
    private lateinit var adapter: GirlsAdapter
    private lateinit var viewModel: GankListViewModel
    companion object {
        const val CATEGORY = "category"
        const val SHOW_TITLE = "show_title"
        fun newInstance(params: Bundle?):GirlsFragment{

            var frag = GirlsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_girls
    }

    override fun initView() {
        binding.rv.layoutManager = StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL)
        UiTools.initSwipRefresh(binding.srl)
    }

    override fun initListener() {
        super.initListener()
        binding.rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            var visibleItems:IntArray = IntArray(spanCount)

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && hasNext ){
                    (recyclerView.layoutManager as StaggeredGridLayoutManager).findLastCompletelyVisibleItemPositions(visibleItems)
                    for(pos  in visibleItems){
                        if(pos >= adapter.itemCount - 1 - (2* spanCount)){
                            //可见view中包含最后2行 就加载下一页
                            loadMore()
                            break
                        }
                    }
                }

            }
        })
        /**
         * 下拉监听
         */
        binding.srl.setOnRefreshListener {
            category?.let {
                pageIndex = 1
                viewModel.loadData(it,pageIndex)
            }
        }
    }

    override fun initData() {

        adapter = GirlsAdapter(context!!)
        adapter.setOnItemClickListener{
                ImageActivity.start(activity!!,ArrayList<String>().apply { add(it.url) })
        }
        binding.rv.adapter = adapter
        category = arguments?.getString(CATEGORY)
        showTitle = arguments?.getBoolean(SHOW_TITLE, true)?:true
        if (!showTitle){
            binding.tvTitle.visibility = View.GONE
        }
        viewModel = ViewModelProviders.of(this).get(GankListViewModel::class.java)
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    if (pageIndex == 1) {
                        binding.sv.showLoading()
                    }
                }
                Resource.SUCCESS->{
                    binding.sv.showContent()
                    binding.srl.isRefreshing = false
                    it.data?.results?.run {
                        if(pageIndex == 1){
                            adapter.update(this)
                        }else{
                            adapter.add(this)
                        }
                        if(this.size < 15){
                            hasNext = false
                        }
                    }
                }
                Resource.ERROR->{
                    binding.srl.isRefreshing = false
                    if(pageIndex == 1){
                        binding.sv.showError()
                    }
                }
            }
        })
        category?.let {
            binding.sv.showLoading()
            viewModel.loadData(it,pageIndex)
        }
    }

    private fun loadMore(){
        category?.let {
            pageIndex++
            viewModel.loadData(it,pageIndex)
        }
    }
}