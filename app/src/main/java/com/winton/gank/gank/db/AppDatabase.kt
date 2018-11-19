package com.winton.gank.gank.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.TypeConverter
import com.winton.gank.gank.repository.entity.SearchKey
import java.util.*

/**
 * @author: winton
 * @time: 2018/11/19 6:30 PM
 * @desc: 应用数据库
 */
@Database(entities ={SearchKey::}, version = 1)
class AppDatabase {

}

object ConversionFactory {

    @TypeConverter
    fun fromDateToLong(date:Date):Long {
        return date.time
    }

    @TypeConverter
    fun fromLongToDate(value:Long ): Date {
        return Date(value)
    }
}