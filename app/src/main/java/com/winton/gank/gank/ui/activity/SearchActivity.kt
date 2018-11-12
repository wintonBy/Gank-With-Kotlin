package com.winton.gank.gank.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.internal.FlowLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.githang.statusbar.StatusBarCompat
import com.winton.gank.gank.R
import com.winton.gank.gank.databinding.ActSearchBinding
import com.winton.gank.gank.ui.BaseActivity
import com.winton.gank.gank.viewmodel.SearchViewModel

/**
 * @author: winton
 * @time: 2018/11/12 8:00 PM
 * @desc: 搜索Activity
 */
class SearchActivity:BaseActivity<ActSearchBinding>() {

    private lateinit var viewModel:SearchViewModel

    override fun getLayoutId() = R.layout.act_search

    private var pageIndex = 1

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
            }
        }
    }

    override fun initPreLayout(savedInstanceState: Bundle?) {
        super.initPreLayout(savedInstanceState)
        StatusBarCompat.setStatusBarColor(this@SearchActivity, Color.WHITE,true)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        viewModel.getListData().observe(this, Observer {

        })
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()
        addKeys()

    }

    override fun onStop() {
        super.onStop()
        viewModel.stop()
    }

    private fun addKeys(){
        val key = TextView(this)
        key.setText("搜索")
        val parms = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        binding.flHisKey.addView(key,parms)
    }
}