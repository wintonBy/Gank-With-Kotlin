package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.FragIndexBinding
import com.winton.gank.gank.ui.BaseFragment

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

    override fun getLayoutId(): Int {
        return R.layout.frag_index
    }
}