package com.neirx.githubsearch.data.room.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neirx.githubsearch.model.Repository

/**
 * Created by Waide Shery on 12.08.19.
 */
class RepositoryTypeConverter{
    companion object{
        val gson = Gson()
    }

    @TypeConverter
    fun repositoriesToJson(repositories: List<Repository>): String {
        return gson.toJson(repositories)
    }

    @TypeConverter
    fun jsonToRepositories(json: String): List<Repository> {
        val listType = object : TypeToken<List<Repository>>(){}.type
        return gson.fromJson(json, listType)
    }
}
