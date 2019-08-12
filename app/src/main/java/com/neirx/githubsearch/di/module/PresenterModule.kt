package com.neirx.githubsearch.di.module

import com.neirx.githubsearch.contract.presenter.SearchPresenterFactory
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.ui.main.presenter.RepositorySearchPresenterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Waide Shery on 11.08.19.
 */
@Module
class PresenterModule {
    @Singleton
    @Provides
    fun provideSearchPresenterFactory(repositoryStorage: RepositoryStorage): SearchPresenterFactory {
        return RepositorySearchPresenterFactory(repositoryStorage)
    }
}