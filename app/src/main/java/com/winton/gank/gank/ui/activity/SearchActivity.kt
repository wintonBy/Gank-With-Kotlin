package com.winton.gank.gank.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.githang.statusbar.StatusBarCompat
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.SearchAdapter
import com.winton.gank.gank.databinding.ActSearchBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.repository.entity.SearchKey
import com.winton.gank.gank.ui.BaseActivity
import com.winton.gank.gank.viewmodel.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import java.util.*

/**
 * @author: winton
 * @time: 2018/11/12 8:00 PM
 * @desc: 搜索Activity
 */
class SearchActivity:BaseActivity<ActSearchBinding>() {

    private lateinit var viewModel:SearchViewModel

    private lateinit var adapter:SearchAdapter

    override fun getLayoutId() = R.layout.act_search

    private var mhisKey:List<SearchKey>? = null

    private var pageIndex = 1

    private var hasNext = true

    companion object {
        fun start(context: Context){
            var intent = Intent(context,SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initListener() {
        super.initListener()
        binding.etKey.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if(it.isNotEmpty()){
                        binding.ibDelete.visibility = View.VISIBLE
                    }else{
                        binding.ibDelete.visibility = View.GONE
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                s?.let {
                    if(it.isNotEmpty()){
                        binding.ibDelete.visibility = View.VISIBLE
                    }else{
                        binding.ibDelete.visibility = View.GONE
                    }
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.ibDelete.setOnClickListener {
            val index = binding.etKey.selectionStart
            binding.etKey.editableText.delete(index-1,index)
        }

        binding.ibSearch.setOnClickListener {
            if(binding.etKey.text.trim().isNotEmpty()){
                pageIndex = 1
                viewModel.loadData(binding.etKey.text.toString(),pageIndex)
                binding.flHisKey.visibility = View.GONE
                binding.status.visibility = View.VISIBLE
                saveKey(binding.etKey.text.toString())
            }
        }
        binding.ibBack.setOnClickListener{
            finish()
        }
    }

    override fun initPreLayout(savedInstanceState: Bundle?) {
        super.initPreLayout(savedInstanceState)
        StatusBarCompat.setStatusBarColor(this@SearchActivity, Color.WHITE,true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        adapter = SearchAdapter(this)
        adapter.setOnItemClickListener(object :SearchAdapter.OnItemClickListener{
            override fun clickItem(item: TitleBean) {
                val url = item?.url
                if(url != null){
                    WebActivity.start(this@SearchActivity,url)
                }else{
                    ToastUtils.showLong("链接为空")
                }
            }
        })
        binding.rvResult.layoutManager = LinearLayoutManager(this)
        binding.rvResult.adapter = adapter
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.start()
        initKey()
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    if(pageIndex == 1){
                        binding.status.showLoading()
                    }
                }
                Resource.SUCCESS->{
                    binding.status.showContent()
                    it.data?.results?.run {
                        if(pageIndex >1){
                            adapter.add(this)
                        }else{
                            adapter.update(this)
                        }
                        //默认每页加载15条数据
                        if(this.size < 15){
                            hasNext = false
                        }
                    }
                }
                Resource.ERROR->{
                    if(pageIndex == 1){
                        binding.status.showError()
                    }else{
                        ToastUtils.showShort("加载出错")
                    }
                }
            }
        })

    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    private fun initKey(){
        viewModel.getKeyData().observe(this, Observer {
            it?.let {
                binding.flHisKey.adapter = object :TagAdapter<SearchKey>(it){
                    override fun getView(parent: FlowLayout, position: Int, t: SearchKey): View {
                        val key = TextView(this@SearchActivity)
                        key.setSingleLine(true)
                        key.text = t.key
                        return key
                    }
                }
            }
        })

    }

    private fun saveKey(key:String){
        val searchKey = SearchKey(key, Date().time)
        viewModel.addKey(searchKey)
    }
}