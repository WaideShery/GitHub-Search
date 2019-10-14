package com.neirx.githubsearch.di

import android.app.Application
import com.neirx.githubsearch.app.ThisApp
import com.neirx.githubsearch.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Waide Shery on 11.08.19.
 */
@Component(
    modules = [
        AndroidInjectionModule::class,
        ContextModule::class,
        AndroidComponentModule::class,
        ViewModelModule::class,
        RepositoryModule::class,
        RoomModule::class,
        RetrofitModule::class]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun contextModule(contextModule: ContextModule): Builder

        fun build(): AppComponent
    }

    fun inject(appController: ThisApp)
}