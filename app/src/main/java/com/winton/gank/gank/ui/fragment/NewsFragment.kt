package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
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


    private var firstVisibleItem = 0
    private var lastVisibleItem = 1

    private val smallVideoHelper: GSYVideoHelper by lazy {
        GSYVideoHelper(activity!!)
    }
    private val smallGSYVideoHelperBuilder: GSYVideoHelper.GSYVideoHelperBuilder by lazy {
        GSYVideoHelper.GSYVideoHelperBuilder().apply {
            this.isHideStatusBar = true
            this.isNeedLockFull = true
            this.isCacheWithPlay = true
            this.isShowFullAnimation = false
            this.isRotateViewAuto = false
            this.isLockLand = true
            this.videoAllCallBack = object : GSYSampleCallBack(){
                override fun onQuitSmallWidget(url: String?, vararg objects: Any?) {
                    super.onQuitSmallWidget(url, *objects)
                    if(smallVideoHelper.playPosition >= 0 && smallVideoHelper.playTAG.equals("")){
                        val pos = smallVideoHelper.playPosition
                        if(pos in firstVisibleItem .. lastVisibleItem){
                            smallVideoHelper.releaseVideoPlayer()
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }

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
        smallVideoHelper.setGsyVideoOptionBuilder(smallGSYVideoHelperBuilder)
        binding.rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
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