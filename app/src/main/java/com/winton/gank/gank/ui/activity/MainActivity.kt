package com.winton.gank.gank.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.blankj.utilcode.util.ToastUtils
import com.githang.statusbar.StatusBarCompat
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper
import com.winton.bottomnavigationview.NavigationView
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActMainBinding
import com.winton.gank.gank.ui.BaseActivity
import com.winton.gank.gank.ui.fragment.*
import android.view.Window.FEATURE_CONTENT_TRANSITIONS
import android.os.Build
import android.transition.Explode
import android.view.Window
import android.widget.Toast
import com.zxing.activity.CodeUtils
import android.R.attr.data




class MainActivity : BaseActivity<ActMainBinding>() {

    private lateinit var mNV: NavigationView
    private lateinit var nvItems: ArrayList<NavigationView.Model>
    private lateinit var fragments: ArrayList<Model>


    /**
     * 上次按下返回键的时间
     */
    private var lastDownBackKeyTime = 0L

    private var currentIndex = -1
    private val fm: FragmentManager by lazy { supportFragmentManager }

    companion object {
        fun start(context: Context){
            var intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
        }

        const val REQ_SCAN = 1
    }

    override fun initPreLayout(savedInstanceState: Bundle?) {
        super.initPreLayout(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }

    }

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
            this.add(Model("girls", JDGirlsFragment.newInstance(null)))
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
                when(index) {
                    3 -> {
                        StatusBarCompat.setStatusBarColor(this@MainActivity, Color.BLACK, false)
                    }
                    else -> {
                        StatusBarCompat.setStatusBarColor(this@MainActivity, Color.WHITE,true)
                    }
                }
            }
        })
        mNV.check(0)
        changeFragment(0)
        StatusBarCompat.setStatusBarColor(this, Color.WHITE,true)
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


    override fun onBackPressed() {
        val currentBackTime = System.currentTimeMillis()
        if(currentBackTime - lastDownBackKeyTime > 2000){
            ToastUtils.showShort("再按一次返回键退出")
            lastDownBackKeyTime = currentBackTime
        }else{
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK){
            return
        }
        when(requestCode){
            REQ_SCAN ->{
                val type = data!!.getIntExtra(CodeUtils.RESULT_TYPE,-1)
                val result = data!!.getStringExtra(CodeUtils.RESULT_STRING)
                if (type == CodeUtils.RESULT_SUCCESS) {
                    ScanResultActivity.start(this@MainActivity,result)
                } else {
                    Toast.makeText(this, "扫描失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
