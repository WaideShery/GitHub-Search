package com.neirx.githubsearch.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neirx.githubsearch.data.room.dao.SearchCacheDao
import com.neirx.githubsearch.data.room.entity.SearchCacheEntity

/**
 * Created by Waide Shery on 12.08.19.
 */
@Database(
    entities = [SearchCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "git_search_db"
        const val TABLE_SEARCH_CACHE = "search_cache"
    }

    abstract fun searchCacheDao(): SearchCacheDao
}