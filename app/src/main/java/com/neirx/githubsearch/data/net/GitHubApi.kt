package com.neirx.githubsearch.data.net

import com.neirx.githubsearch.data.net.response.SearchRepositoriesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Waide Shery on 12.08.19.
 */
interface GitHubApi{
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/repositories")
    fun searchRepositories(@Query("q") query: String): Observable<SearchRepositoriesResponse>
}