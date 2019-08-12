package com.neirx.githubsearch.di.module

import com.google.gson.Gson
import com.neirx.githubsearch.BuildConfig
import com.neirx.githubsearch.data.net.GitHubApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Waide Shery on 12.08.19.
 */
@Module
class RetrofitModule{
    @Singleton
    @Provides
    fun provideGson(): Gson{
        return Gson()
    }

    @Provides
    internal fun provideClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor { message -> Timber.d(message) }
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideGitHubApi(clientBuilder: OkHttpClient.Builder): GitHubApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.github.com/")
            .client(clientBuilder.build())
            .build()
            .create(GitHubApi::class.java)
    }
}