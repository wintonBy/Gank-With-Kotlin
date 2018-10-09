package com.winton.gank.gank.fragment

import android.os.Bundle

/**
 * @author: winton
 * @time: 2018/10/9 下午7:47
 * @desc: 新闻页
 */
class NewsFragment:BaseFragment() {
    companion object {
        fun newInstance(params: Bundle?):NewsFragment{
            var frag = NewsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }

    }
}