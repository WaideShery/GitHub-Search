package com.neirx.githubsearch.presenter

import com.neirx.githubsearch.RepositoryUtil
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.model.Repository
import io.reactivex.Observable

/**
 * Created by Waide Shery on 12.08.19.
 */
class RepositorySearchStorage : RepositoryStorage {

    override fun search(query: String): Observable<List<Repository>> {
        return if (query == "Test") Observable.create {
            it.onNext(repositories)
        } else Observable.create {
            it.onError(throwable)
        }
    }

    companion object {
        val repositories: List<Repository> = listOf(
            RepositoryUtil.randomRepository()
        )
        val throwable: Throwable = NullPointerException("Repositories is null")

        fun get(): RepositorySearchStorage {
            return RepositorySearchStorage()
        }
    }


}
