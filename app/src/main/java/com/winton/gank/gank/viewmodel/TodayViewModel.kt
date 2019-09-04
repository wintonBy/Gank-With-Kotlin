package com.winton.gank.gank.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.CategoryResponse
import com.winton.gank.gank.repository.Resource

/**
 * @author: winton
 * @time: 2018/10/22 下午8:54
 * @desc: 今日数据
 */
class TodayViewModel : BaseViewModel() {

    companion object{
        const val GIFT = "福利"
        const val ANDROID = "Android"
        const val IOS = "iOS"
        const val APP = "App"
        const val RECOMMEND = "瞎推荐"

        const val ST_NONE = 0x00
        const val ST_GIFT = 0x01
        const val ST_ANDROID = 0x01.shl(1)
        const val ST_IOS = 0x01.shl(2)
        const val ST_APP = 0x01.shl(3)
        const val ST_RECOMMEND = 0x01.shl(4)

    }

    class TodayData {
        val data = ArrayList<IndexItem>()

        fun reset() {
            data.clear()
        }
    }

    private val giftModel: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    private val androidModel: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    private val iosModel: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    private val appModel: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()
    private val recommendModel: MutableLiveData<Resource<CategoryResponse>> = MutableLiveData()

    val todayDataModel: MutableLiveData<Resource<TodayData>> = MutableLiveData()

    val todayValue = TodayData()

    @Volatile
    private var mStatus = ST_NONE

    @Synchronized
    private fun checkStatue() {
        if (mStatus.and(ST_GIFT) == 0) return
        if (mStatus.and(ST_ANDROID) == 0) return
        if (mStatus.and(ST_IOS) == 0) return
        if (mStatus.and(ST_APP) == 0) return
        if (mStatus.and(ST_RECOMMEND) == 0) return

        //if all step is finished, notify data
        todayDataModel.value = Resource.success(todayValue)
    }


    override fun start() {
        todayDataModel.value = Resource.loading(null)
        mStatus = ST_NONE
        todayValue.reset()
        RetrofitHolder.gankInstance().category(GIFT,1, 5, giftRequest)
        RetrofitHolder.gankInstance().category(ANDROID, 1, 10, androidRequest)
        RetrofitHolder.gankInstance().category(IOS, 1, 10, iOSRequest)
        RetrofitHolder.gankInstance().category(APP, 1, 10, appRequest)
        RetrofitHolder.gankInstance().category(RECOMMEND, 1, 10, recommendRequest)
    }

    override fun stop() {
        giftRequest.mSub?.cancel()
        androidRequest.mSub?.cancel()
        iOSRequest.mSub?.cancel()
        appRequest.mSub?.cancel()
        recommendRequest.mSub?.cancel()
    }

    private val giftRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_GIFT)
            t.results?.let {
                todayValue.data.add(0,IndexItem(IndexAdapter.T_IMAGE, it))
            }
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            giftModel.value = Resource.error(null, errorMsg)
        }
    }

    private val androidRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            t.results?.let {
                for (i in it.indices) {
                    when(i) {
                        0 -> todayValue.data.add( IndexItem(IndexAdapter.T_TITLE, it[i]))
                        it.size-1 -> todayValue.data.add( IndexItem(IndexAdapter.T_END, it[i]))
                        else -> todayValue.data.add( IndexItem(IndexAdapter.T_CONTENT, it[i]))
                    }
                }
            }
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            androidModel.value = Resource.error(null, errorMsg)
        }
    }

    private val iOSRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            iosModel.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            iosModel.value = Resource.error(null, errorMsg)
        }
    }

    private val appRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            appModel.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            appModel.value = Resource.error(null, errorMsg)
        }
    }

    private val recommendRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            recommendModel.value = Resource.success(t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            recommendModel.value = Resource.error(null, errorMsg)
        }
    }
}