package com.neirx.githubsearch.app

import android.app.Activity
import android.app.Application
import com.neirx.githubsearch.BuildConfig
import com.neirx.githubsearch.di.DaggerAppComponent
import com.neirx.githubsearch.di.module.ContextModule
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Waide Shery on 11.08.19.
 */
class ThisApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        DaggerAppComponent
            .builder()
            .application(this)
            .contextModule(ContextModule(this))
            .build()
            .inject(this)

    }
}