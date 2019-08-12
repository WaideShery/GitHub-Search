package com.neirx.githubsearch.di.module

import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.data.net.GitHubApi
import com.neirx.githubsearch.data.repository.GitHubStorage
import com.neirx.githubsearch.data.room.dao.SearchCacheDao
import dagger.Module
import dagger.Provides

/**
 * Created by Waide Shery on 12.08.19.
 */
@Module
class RepositoryModule {
    @Provides
    fun bindRepositoryStorage(searchCacheDao: SearchCacheDao, gitHubApi: GitHubApi): RepositoryStorage {
        return GitHubStorage(searchCacheDao, gitHubApi)
    }
}
