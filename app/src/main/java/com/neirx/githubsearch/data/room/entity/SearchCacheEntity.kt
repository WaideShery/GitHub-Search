package com.neirx.githubsearch.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.neirx.githubsearch.data.room.AppDatabase
import com.neirx.githubsearch.data.room.type_converter.RepositoryTypeConverter
import com.neirx.githubsearch.model.Repository

/**
 * Created by Waide Shery on 12.08.19.
 */
@Entity(tableName = AppDatabase.TABLE_SEARCH_CACHE)
class SearchCacheEntity(query: String) {
    @PrimaryKey()
    @ColumnInfo(name = "query")
    var query: String = query

    @ColumnInfo(name = "time")
    var time: Long = 0

    @TypeConverters(value = [RepositoryTypeConverter::class])
    @ColumnInfo(name = "data")
    lateinit var repositories: List<Repository>

}
