package com.winton.gank.gank.db.dao

import androidx.room.*
import com.winton.gank.gank.repository.entity.SearchKey
import io.reactivex.Flowable

/**
 * @author: winton
 * @time: 2018/11/20 10:13 AM
 * @desc: SearchDao
 */
@Dao
interface SearchKeyDao {
    @Query("SELECT * FROM SEARCH_KEY_TB ORDER BY time LIMIT 20")
    fun getKeys():Flowable<List<SearchKey>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(key:SearchKey)

    @Delete
    fun delete(key:SearchKey)

    @Query("DELETE FROM SEARCH_KEY_TB")
    fun deleteAll()
}