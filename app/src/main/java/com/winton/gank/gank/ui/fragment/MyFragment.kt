package com.winton.gank.gank.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.blankj.utilcode.util.LogUtils
import com.githang.statusbar.StatusBarCompat
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.ItemTouchHelperCallBack
import com.winton.gank.gank.adapter.MeAdapter
import com.winton.gank.gank.databinding.FragMeBinding
import com.winton.gank.gank.http.bean.PersonCenterBean
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.utils.BindingUtils
import com.winton.gank.gank.utils.diffutils.PersonCenterBeanDiff
import com.winton.gank.gank.utils.glide.GlideApp
import com.winton.gank.gank.widget.CommItemDecoration

/**
 * @author: winton
 * @time: 2018/10/9 下午7:48
 * @desc: 我的
 */
class MyFragment: BaseFragment<FragMeBinding>() {

    companion object {
        fun newInstance(params: Bundle?):MyFragment{
            var frag = MyFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    private lateinit var adapter: MeAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper

    private val mData:ArrayList<PersonCenterBean> by lazy {
        ArrayList<PersonCenterBean>().apply {
            this.add(PersonCenterBean("我的GitHub").apply {iconId = R.mipmap.github})
            this.add(PersonCenterBean("我的CSDN").apply {iconId = R.mipmap.csdn})
            this.add(PersonCenterBean("我的BLOG").apply {iconId = R.mipmap.blog})

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_me
    }

    override fun initView() {
        super.initView()
        binding.rv.layoutManager = GridLayoutManager(context!!,3)
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initData() {
        super.initData()
        adapter = MeAdapter(context!!)
        adapter.setOnItemClickListener(object :MeAdapter.OnItemClick{
            override fun onItemClick(item: PersonCenterBean) {
            }
        })
        adapter.setOnItemLongClickListener(object :MeAdapter.OnItemLongClick{
            override fun onItemLongClick(viewHolder: RecyclerView.ViewHolder): Boolean {
                itemTouchHelper.startDrag(viewHolder)
                return true
            }
        })
        adapter.update(mData)
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.rv.adapter = adapter
    }

}