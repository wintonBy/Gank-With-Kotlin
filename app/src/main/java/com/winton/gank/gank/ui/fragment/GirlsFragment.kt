package com.winton.gank.gank.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.winton.gank.gank.R
import com.winton.gank.gank.adapter.GirlsAdapter
import com.winton.gank.gank.adapter.mulitype.GankListAdapter
import com.winton.gank.gank.databinding.FragGirlsBinding
import com.winton.gank.gank.http.bean.TitleBean
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.ui.BaseFragment
import com.winton.gank.gank.ui.activity.ImageActivity
import com.winton.gank.gank.utils.UiTools
import com.winton.gank.gank.utils.diffutils.GankGirlsDiff
import com.winton.gank.gank.viewmodel.GankListViewModel

/**
 * @author: winton
 * @time: 2018/10/9 下午7:48
 * @desc: girl fragment
 */
class GirlsFragment: BaseFragment<FragGirlsBinding>() {

    private var category:String? = null
    private var pageIndex = 1
    private lateinit var adapter: GirlsAdapter
    private lateinit var viewModel: GankListViewModel
    companion object {
        const val CATEGORY = "category"
        fun newInstance(params: Bundle?):GirlsFragment{

            var frag = GirlsFragment()
            params?.apply { frag.arguments = this }
            return frag
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.frag_girls
    }

    override fun initView() {
        binding.rv.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        UiTools.initSwipRefresh(binding.srl)
    }

    override fun initData() {
        adapter = GirlsAdapter(GankGirlsDiff(),context!!)
        adapter.setOnItemClickListener(object :GirlsAdapter.OnItemClick{
            override fun onItemClick(item: TitleBean) {
                ImageActivity.start(activity!!,item.url)
            }
        })
        binding.rv.adapter = adapter
        category = arguments?.getString(GankListFragment.CATEGORY)
        viewModel = ViewModelProviders.of(this).get(GankListViewModel::class.java)
        viewModel.getListData().observe(this, Observer {
            when(it?.status){
                Resource.LOADING ->{
                    binding.sv.showLoading()
                }
                Resource.SUCCESS->{
                    binding.sv.showContent()
                    it.data?.results?.run {
                        adapter.submitList(this)
                    }
                }
                Resource.ERROR->{
                    binding.sv.showError()
                }
            }
        })
        category?.let {
            binding.sv.showLoading()
            viewModel.loadData(it,pageIndex)
        }
    }
}