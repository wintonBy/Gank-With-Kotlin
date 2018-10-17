package com.winton.gank.gank.repository

/**
 * @author: winton
 * @time: 2018/10/17 下午3:46
 * @desc: 描述
 */
class Resource<T:Any?> {

    val status:Int
    val data:T?
    val msg:String?

    constructor(status: Int, data: T?, msg: String?) {
        this.status = status
        this.data = data
        this.msg = msg
    }


    companion object {
        const val ERROR = 0
        const val LOADING = 1
        const val SUCCESS = 2

        fun <T> success(data:T):Resource<T>{
            return Resource(SUCCESS,data,null)
        }

        fun <T> error(data:T?,msg: String?):Resource<T>{
            return Resource(ERROR,data,msg)
        }

        fun <T> loading(data:T?):Resource<T>{
            return  Resource(LOADING,data,null)
        }
    }

}