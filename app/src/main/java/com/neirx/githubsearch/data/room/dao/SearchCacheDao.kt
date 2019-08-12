package com.neirx.githubsearch.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.neirx.githubsearch.data.room.entity.SearchCacheEntity

/**
 * Created by Waide Shery on 12.08.19.
 */
@Dao
abstract class SearchCacheDao {
    @Insert
    abstract fun insert(entity: SearchCacheEntity)

    @Query("DELETE FROM search_cache WHERE time <= :toTime")
    abstract fun clear(toTime: Long)

    @Query("SELECT * FROM search_cache WHERE `query` LIKE :query")
    abstract fun select(query: String): SearchCacheEntity
}