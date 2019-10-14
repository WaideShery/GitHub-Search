package com.neirx.githubsearch.di.module

import com.neirx.githubsearch.ui.main.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by Waide Shery on 11.08.19.
 */
@Module(includes = [AndroidInjectionModule::class])
abstract class AndroidComponentModule{

    //-------- Activity injectors BEGIN--------
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeMainActivity() : MainActivity
    //-------- Activity injectors END--------

}