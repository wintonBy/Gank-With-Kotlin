package com.winton.gank.gank.repository.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * @author: winton
 * @time: 2018/11/19 6:37 PM
 * @desc: 描述
 */
@Entity(tableName = "search_key_tb")
data class SearchKey(

        @ColumnInfo(name = "key")
        val key:String,
        @ColumnInfo(name = "time")
        val time: Date)



