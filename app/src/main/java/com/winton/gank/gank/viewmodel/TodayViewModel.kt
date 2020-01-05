package com.winton.gank.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.winton.gank.gank.adapter.mulitype.IndexAdapter
import com.winton.gank.gank.adapter.mulitype.IndexItem
import com.winton.gank.gank.http.BaseGankSubscriber
import com.winton.gank.gank.http.ErrorCode
import com.winton.gank.gank.http.RetrofitHolder
import com.winton.gank.gank.http.response.gank.CategoryResponse
import com.winton.gank.gank.repository.Resource
import kotlin.random.Random

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
        val data = ArrayList<IndexItem>(41)

        fun reset() {
            data.clear()
        }
    }

    private var isError = false

    val todayDataModel: MutableLiveData<Resource<TodayData>> = MutableLiveData()

    private val todayValue = TodayData()

    @Volatile
    private var mStatus = ST_NONE

    @Synchronized
    private fun checkStatue() {
        if (isError) {
            todayDataModel.value = Resource.error(null, "请求出错")
            return
        }
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

    private fun dealResponse(type: String, response: CategoryResponse) {
        if (response.results == null) {
            isError = true
            checkStatue()
            return
        }

        when(type) {
            GIFT -> todayValue.data.add(0,IndexItem(IndexAdapter.T_IMAGE, response.results!!))
            else ->
                for (i in response.results!!.indices) {
                    response.results!![i].views = Random.nextInt(10, 10000)
                    todayValue.data.add( IndexItem(IndexAdapter.T_CONTENT, response.results!![i]))
                }

        }
        checkStatue()
    }

    private val giftRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_GIFT)
            dealResponse(GIFT, t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            mStatus = mStatus.or(ST_GIFT)
            isError = true
            checkStatue()
        }
    }

    private val androidRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_ANDROID)
            dealResponse(ANDROID, t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            mStatus = mStatus.or(ST_ANDROID)
            isError = true
            checkStatue()
        }
    }

    private val iOSRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_IOS)
            dealResponse(IOS, t)
        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            mStatus = mStatus.or(ST_IOS)
            isError = true
            checkStatue()
        }
    }

    private val appRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_APP)
            dealResponse(APP, t)        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            mStatus = mStatus.or(ST_APP)
            isError = true
            checkStatue()
        }
    }

    private val recommendRequest = object: BaseGankSubscriber<CategoryResponse>() {
        override fun onSuccess(t: CategoryResponse) {
            mStatus = mStatus.or(ST_RECOMMEND)
            dealResponse(RECOMMEND, t)        }

        override fun onFail(code: ErrorCode, errorMsg: String) {
            mStatus = mStatus.or(ST_RECOMMEND)
            isError = true
            checkStatue()
        }
    }
}