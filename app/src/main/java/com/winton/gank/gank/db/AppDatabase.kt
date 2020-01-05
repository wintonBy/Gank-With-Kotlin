package com.winton.gank.gank.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.winton.gank.gank.db.dao.SearchKeyDao
import com.winton.gank.gank.repository.entity.SearchKey

/**
 * @author: winton
 * @time: 2018/11/19 6:30 PM
 * @desc: 应用数据库
 */
@Database(entities =[SearchKey::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

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