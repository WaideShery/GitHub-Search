package com.neirx.githubsearch.di.module

import android.content.Context
import androidx.room.Room
import com.neirx.githubsearch.data.room.AppDatabase
import com.neirx.githubsearch.data.room.dao.SearchCacheDao
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Waide Shery on 12.08.19.
 */
@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@Named("AppContext") context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchCacheDao(appDatabase: AppDatabase): SearchCacheDao {
        return appDatabase.searchCacheDao()
    }

}