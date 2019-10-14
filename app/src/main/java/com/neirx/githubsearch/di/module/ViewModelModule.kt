package com.neirx.githubsearch.di.module

import androidx.lifecycle.ViewModelProviders
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.ui.main.MainActivity
import com.neirx.githubsearch.ui.main.MainViewModel
import dagger.Module
import dagger.Provides

/**
 * Created by Waide Shery on 14.10.19.
 */
@Module
class ViewModelModule {
    @Provides
    fun provideMainViewModel(
        target: MainActivity,
        repositoryStorage: RepositoryStorage
    ): MainViewModel =
        ViewModelProviders.of(target, MainViewModel.Factory(repositoryStorage))
            .get(MainViewModel::class.java)

}