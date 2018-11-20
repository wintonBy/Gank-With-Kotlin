package com.winton.gank.gank.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.winton.gank.gank.db.dao.SearchKeyDao
import com.winton.gank.gank.repository.entity.SearchKey

/**
 * @author: winton
 * @time: 2018/11/19 6:30 PM
 * @desc: 应用数据库
 */
@Database(entities =[SearchKey::class], version = 1,exportSchema = false)
abstract class AppDatabase :RoomDatabase() {

    companion object {
        @Volatile private var INSTANCE:AppDatabase? = null
        /**
         * 获取数据库实例
         */
        fun getInstance(context: Context):AppDatabase =
                INSTANCE?: synchronized(this){
                    INSTANCE?:buildDatabase(context).also{ INSTANCE = it}
                }
        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,
                        "gank.db")
                        .build()
    }

    abstract fun getSearchDao():SearchKeyDao

}