package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import com.githang.statusbar.StatusBarCompat
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.IndexVPAdapter
import com.winton.gank.gank.databinding.FragIndexBinding
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
    private lateinit var adapter: IndexVPAdapter
    private val framents by lazy {
        ArrayList<Fragment>().apply {
            this.add(TodayFragment.newInstance(null))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"Android") }))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"iOS") }))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"App") }))
            this.add(GirlsFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"福利") }))
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        adapter = IndexVPAdapter(fragmentManager,framents)
        binding.vp.adapter = adapter
        binding.vp.offscreenPageLimit = framents.size
        binding.tabIndex.setupWithViewPager(binding.vp)
        initTab()
        viewModel.start()
    }

    private fun initTab(){
        binding.tabIndex.getTabAt(0)?.text = "今日推荐"
        binding.tabIndex.getTabAt(1)?.text = "Android"
        binding.tabIndex.getTabAt(2)?.text = "iOS"
        binding.tabIndex.getTabAt(3)?.text = "App"
        binding.tabIndex.getTabAt(4)?.text = "福利"
    }

    override fun onResume() {
        super.onResume()
        StatusBarCompat.setStatusBarColor(activity, Color.WHITE,true)
    }
}