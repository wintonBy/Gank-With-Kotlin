package com.winton.gank.gank.ui.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.IndexVPAdapter
import com.winton.gank.gank.databinding.FragIndexBinding
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.MainActivity
import com.winton.gank.gank.ui.activity.SearchActivity
import com.winton.gank.gank.viewmodel.IndexViewModel
import com.zxing.activity.ScanActivity

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
            this.add(GirlsFragment.newInstance(Bundle().apply {
                this.putString(GirlsFragment.CATEGORY, "福利")
                this.putBoolean(GirlsFragment.SHOW_TITLE, false)
            }))
            this.add(TodayFragment.newInstance(null))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"Android") }))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"iOS") }))
            this.add(GankListFragment.newInstance(Bundle().apply { this.putString(GankListFragment.CATEGORY,"App") }))
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }

    override fun initListener() {
        super.initListener()
        //点击扫码
        binding.ibScan.setOnClickListener {
            ScanActivity.start(activity,MainActivity.REQ_SCAN,true)
        }
        //点击搜索
        binding.tvRcSearch.setOnClickListener {
            binding.tvRcSearch.isClickable  = false
            SearchActivity.start(context!!)
            binding.tvRcSearch.isClickable  = true
        }

    }

    override fun initData() {
        super.initData()
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        adapter = IndexVPAdapter(fragmentManager!!, framents)
        binding.vp.adapter = adapter
        binding.vp.offscreenPageLimit = framents.size
        binding.tabIndex.setupWithViewPager(binding.vp)
        initTab()
        viewModel.start()
    }

    private fun initTab(){
        binding.tabIndex.isTabIndicatorFullWidth
        binding.tabIndex.run {
            getTabAt(0)?.customView = getTab(0).apply { findViewById<TextView>(R.id.tab_title).text="福利" }
            getTabAt(1)?.customView = getTab(1).apply { findViewById<TextView>(R.id.tab_title).text="今日推荐" }
            getTabAt(2)?.customView = getTab(2).apply { findViewById<TextView>(R.id.tab_title).text="Android" }
            getTabAt(3)?.customView = getTab(3).apply { findViewById<TextView>(R.id.tab_title).text="iOS" }
            getTabAt(4)?.customView = getTab(4).apply { findViewById<TextView>(R.id.tab_title).text="App" }
        }
        binding.tabIndex.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.customView!!.findViewById<TextView>(R.id.tab_title).run {
                    setTextColor(resources.getColor(android.R.color.black))
                    ValueAnimator.ofFloat(18F,14F).apply {
                        duration = 200
                        addUpdateListener {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP,(it.animatedValue as Float));
                        }
                    }.start()
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.customView!!.findViewById<TextView>(R.id.tab_title).run {
                    setTextColor(resources.getColor(R.color.tab_selected_text_color))
                    ValueAnimator.ofFloat(14F,18F).apply {
                        duration = 200
                        addUpdateListener {
                            setTextSize(TypedValue.COMPLEX_UNIT_DIP,(it.animatedValue as Float));
                        }
                    }.start()
                }
            }
        })
        binding.tabIndex.getTabAt(1)!!.select()

    }

    private fun getTab(pos: Int) = LayoutInflater.from(context!!).inflate(R.layout.layout_tab,null)


}