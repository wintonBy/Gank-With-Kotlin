package com.winton.gank.gank.fragment

import android.os.Bundle

/**
 * @author: winton
 * @time: 2018/10/9 下午7:48
 * @desc: girl fragment
 */
class GirlsFragment:BaseFragment() {

    companion object {
        fun newInstance(params: Bundle?):GirlsFragment{
            var frag = GirlsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }

    }
}