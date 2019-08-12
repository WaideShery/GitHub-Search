package com.neirx.githubsearch.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by Waide Shery on 12.08.19.
 */
@Module
class ContextModule(private val context: Context) {

    @Provides
    @Named("AppContext")
    internal fun getContext(): Context {
        return context
    }
}