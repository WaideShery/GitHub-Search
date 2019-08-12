package com.neirx.githubsearch.di.module

import com.neirx.githubsearch.contract.view.SearchView
import com.neirx.githubsearch.ui.main.MainActivity
import dagger.Binds
import dagger.Module



/**
 * Created by Waide Shery on 11.08.19.
 */
@Module
abstract class ViewModule{
    @Binds
    abstract fun bindSearchView(activity: MainActivity): SearchView

}