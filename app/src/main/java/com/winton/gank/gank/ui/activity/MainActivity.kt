package com.winton.gank.gank.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.winton.bottomnavigationview.NavigationView
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActMainBinding
import com.winton.gank.gank.ui.BaseActivity
import com.winton.gank.gank.ui.fragment.GirlsFragment
import com.winton.gank.gank.ui.fragment.IndexFragment
import com.winton.gank.gank.ui.fragment.MyFragment
import com.winton.gank.gank.ui.fragment.NewsFragment


class MainActivity : BaseActivity<ActMainBinding>() {

    private lateinit var mNV: NavigationView
    private lateinit var nvItems: ArrayList<NavigationView.Model>
    private lateinit var fragments: ArrayList<Model>

    private var currentIndex = -1
    private val fm: FragmentManager by lazy { supportFragmentManager }

    override fun getLayoutId(): Int {
        return R.layout.act_main
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mNV = binding.nv
        initFragments()
        initNavigation()
    }

    /**
     * 初始化fragments
     */
    private fun initFragments() {
        fragments = ArrayList()
        fragments.apply {
            this.add(Model("index", IndexFragment.newInstance(null)))
            this.add(Model("news", NewsFragment.newInstance(null)))
            this.add(Model("girls", GirlsFragment.newInstance(null)))
            this.add(Model("me", MyFragment.newInstance(null)))
        }
    }

    /**
     * 初始化底部导航
     */
    private fun initNavigation() {
        nvItems = ArrayList()
        nvItems.add(NavigationView.Model.Builder(R.mipmap.index_check, R.mipmap.index_uncheck).title("首页").build())
        nvItems.add(NavigationView.Model.Builder(R.mipmap.news_check, R.mipmap.news_uncheck).title("新闻").build())
        nvItems.add(NavigationView.Model.Builder(R.mipmap.girl_check, R.mipmap.girl_uncheck).title("打望").build())
        nvItems.add(NavigationView.Model.Builder(R.mipmap.me_check, R.mipmap.me_uncheck).title("我的").build())
        mNV.setItems(nvItems)
        mNV.build()
        mNV.setOnTabSelectedListener(object : NavigationView.OnTabSelectedListener {
            override fun unselected(index: Int, model: NavigationView.Model?) {
            }

            override fun selected(index: Int, model: NavigationView.Model?) {
                changeFragment(index)
            }
        })
        mNV.check(0)
        changeFragment(0)
    }

    /**
     * 切换fragment
     */
    private fun changeFragment(index: Int) {
        if (index == currentIndex) {
            return
        }
        val lastIndex = currentIndex
        currentIndex = index
        val model = fragments[index]
        val ft = fm.beginTransaction()
        if (!model.fragment.isAdded) {
            ft.add(R.id.fl_content, model.fragment, model.tag)
        }
        if (lastIndex != -1) {
            ft.hide(fragments[lastIndex].fragment)
        }
        ft.show(model.fragment)
        ft.commit()
    }

    private class Model(tag: String, fragment: Fragment) {
        val tag = tag
        val fragment = fragment
    }
}
