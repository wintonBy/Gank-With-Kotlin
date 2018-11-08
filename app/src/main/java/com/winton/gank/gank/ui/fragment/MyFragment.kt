package com.winton.gank.gank.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.blankj.utilcode.util.SPUtils
import com.winton.gank.gank.App
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.ItemTouchHelperCallBack
import com.winton.gank.gank.adapter.MeAdapter
import com.winton.gank.gank.databinding.FragMeBinding
import com.winton.gank.gank.http.bean.BeanType
import com.winton.gank.gank.http.bean.PersonCenterBean
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.WebActivity
import com.winton.gank.gank.utils.ACache
import java.util.*

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
            this.add(PersonCenterBean("我的GitHub")
                    .apply {
                        iconId = R.mipmap.github
                        url = "https://github.com/wintonBy"
                     }
            )
            this.add(PersonCenterBean("我的CSDN")
                    .apply {
                        iconId = R.mipmap.csdn
                        url = "https://blog.csdn.net/wenwen091100304"
                    }
            )
            this.add(PersonCenterBean("我的BLOG")
                    .apply {
                        iconId = R.mipmap.blog
                        url = "https://wintonby.github.io"
                    }
            )
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
                when(item.type){
                    BeanType.URL ->{
                        WebActivity.start(context!!,item.url)
                    }
                    BeanType.ACTIVITY ->{
                        startActivity(item.intent)
                    }
                }
            }
        })
        adapter.setOnItemLongClickListener(object :MeAdapter.OnItemLongClick{
            override fun onItemLongClick(viewHolder: RecyclerView.ViewHolder): Boolean {
                itemTouchHelper.startDrag(viewHolder)
                return true
            }
        })
        fixDataOrderByAcache()
        adapter.update(mData)
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallBack(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rv)
        binding.rv.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveDataOrder()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDataOrder()
    }

    /**
     * 保存个人中心块的顺序
     */
    private fun saveDataOrder(){
        val data = adapter.getData()
        for(i in data.indices){
            SPUtils.getInstance().put(data[i].title,i)
        }
    }

    /**
     * 从缓存中恢复列表的顺序
     */
    private fun fixDataOrderByAcache(){
        for(i in mData.indices){
            mData[i].order = SPUtils.getInstance().getInt(mData[i].title)
        }
        Collections.sort(mData)
    }

}