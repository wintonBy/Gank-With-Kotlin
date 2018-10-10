package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.FragGirlsBinding
import com.winton.gank.gank.ui.BaseFragment

/**
 * @author: winton
 * @time: 2018/10/9 下午7:48
 * @desc: girl fragment
 */
class GirlsFragment: BaseFragment<FragGirlsBinding>() {

    companion object {
        fun newInstance(params: Bundle?):GirlsFragment{
            var frag = GirlsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }
}