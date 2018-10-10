package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.FragNewsBinding
import com.winton.gank.gank.ui.BaseFragment

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

    override fun getLayoutId(): Int {
        return R.layout.frag_news
    }
}