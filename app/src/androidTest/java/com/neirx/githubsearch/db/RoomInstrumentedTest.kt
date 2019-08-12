package com.neirx.githubsearch.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.neirx.githubsearch.RepositoryInstrumentedUtil
import com.neirx.githubsearch.data.room.AppDatabase
import com.neirx.githubsearch.data.room.dao.SearchCacheDao
import com.neirx.githubsearch.data.room.entity.SearchCacheEntity
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * Created by Waide Shery on 12.08.19.
 */
@RunWith(AndroidJUnit4::class)
class RoomInstrumentedTest {
    private lateinit var cacheDao: SearchCacheDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cacheDao = db.searchCacheDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveCacheTest() {
        val entity = SearchCacheEntity("Test")
        entity.time = System.currentTimeMillis()
        entity.repositories = RepositoryInstrumentedUtil.randomRepositories(4)

        cacheDao.clear(entity.time)
        cacheDao.insert(entity)

        var resEntity = cacheDao.select("Test")
        assertThat(resEntity.query, equalTo(entity.query))

        resEntity = cacheDao.select("Any")
        assertThat(resEntity, `is`(nullValue()))
    }
}
