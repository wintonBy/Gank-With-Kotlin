package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.FragTodayBinding
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.widget.CommItemDecoration

/**
 * @author: winton
 * @time: 2018/10/22 下午8:51
 * @desc: 描述
 */
class TodayFragment:BaseFragment<FragTodayBinding>() {

    companion object {
        fun newInstance(params: Bundle?):TodayFragment{
            var frag = TodayFragment()
            params?.apply { frag.arguments = this }
            return frag
        }

    }

    override fun initView() {
        super.initView()
        binding.rvIndex.layoutManager = LinearLayoutManager(context)
        binding.rvIndex.addItemDecoration(CommItemDecoration.createVertical(context!!, App.INSTANCE.resources.getColor(R.color.divider_line),2))
        binding.srl.setOnRefreshListener {
            viewModel.start()
        }

    }
    override fun getLayoutId(): Int {
        return R.layout.frag_today
    }
}