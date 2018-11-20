package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.winton.gank.gank.App
import com.winton.gank.gank.db.AppDatabase
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.CategoryResponse
import com.winton.gank.gank.repository.Resource
import com.winton.gank.gank.repository.entity.SearchKey
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author: winton
 * @time: 2018/11/12 8:32 PM
 * @desc: 搜索ViewModel
 */
class SearchViewModel:BaseViewModel() {

    private var listData: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()

    private var keyData:MutableLiveData<List<SearchKey>> = MutableLiveData()

    private var disposable:CompositeDisposable = CompositeDisposable()

    fun getListData(): MutableLiveData<Resource<CategoryResponse>> = listData

    fun getKeyData():MutableLiveData<List<SearchKey>> = keyData


    override fun stop() {
        super.stop()
        listRequest.mSub?.cancel()
    }


    fun loadData(key:String,pageIndex:Int){
        RetrofitHolder.gankInstance().search(key,"all",pageIndex,listRequest)
    }

    override fun start() {
        super.start()
        loadKey()

    }

    private fun loadKey(){
        disposable.add(AppDatabase.getInstance(App.INSTANCE).getSearchDao().getKeys()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { keyData.value = it})
    }

    fun addKey(key:SearchKey){
        disposable.add(saveKey(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun clearKey(){
        disposable.add(deleteKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    loadKey()
                })
    }

    private fun deleteKey():Completable{
        return Completable.fromAction {
            AppDatabase.getInstance(App.INSTANCE).getSearchDao().deleteAll()
        }
    }

    private fun saveKey(key: SearchKey):Completable{
        return Completable.fromAction {
            AppDatabase.getInstance(App.INSTANCE).getSearchDao().insert(key)
        }
    }


    private val listRequest = object : BaseGankSubscriber<CategoryResponse>(){
        override fun start() {
            super.start()
            listData.value = Resource.loading(null)
        }

        override fun onSuccess(t: CategoryResponse) {
            listData.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            listData.value = Resource.error(null,errorMsg)
        }
    }
}