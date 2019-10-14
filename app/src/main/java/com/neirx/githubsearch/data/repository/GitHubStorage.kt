package com.neirx.githubsearch.data.repository

import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.data.net.GitHubApi
import com.neirx.githubsearch.data.net.item.RepositoryItem
import com.neirx.githubsearch.data.repository.base.Loadable
import com.neirx.githubsearch.data.room.dao.SearchCacheDao
import com.neirx.githubsearch.data.room.entity.SearchCacheEntity
import com.neirx.githubsearch.model.Repository
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit

/**
 * Created by Waide Shery on 12.08.19.
 */
class GitHubStorage(
    val searchCacheDao: SearchCacheDao, val gitHubApi: GitHubApi,
    cacheTime: Long = 30, timeUnit: TimeUnit = TimeUnit.MINUTES
) : RepositoryStorage {

    private var cacheTimeInMillis = timeUnit.toMillis(cacheTime)


    override fun search(query: String): Observable<Loadable<List<Repository>>> {
        val db = Single.fromCallable {
            val time = System.currentTimeMillis() - cacheTimeInMillis
            searchCacheDao.clear(time)
            searchCacheDao.select(query)
        }.map { entity ->
            entity.repositories
        }.onErrorReturnItem(emptyList()).toObservable()

        val net = gitHubApi.searchRepositories(query)
            .map { resp ->
                val repositories = convert(resp.items)
                saveToDB(query, repositories)
                repositories
            }

        return db.flatMap { t ->
            if (t.isEmpty()) {
                Observable.merge(Observable.just(Loadable.loading()),
                    net.map {
                        Loadable.complete(it)
                    })
            } else {
                Observable.just(Loadable.complete(t))
            }
        }
    }

    private fun convert(items: List<RepositoryItem>): List<Repository> {
        val repositories: MutableList<Repository> = ArrayList(items.size)
        for (item in items) {
            val rep = Repository(
                item.id, item.fullName, item.description,
                item.stars, item.htmlUrl
            )
            repositories.add(rep)
        }
        return repositories
    }

    private fun saveToDB(query: String, repositories: List<Repository>) {
        val entity = SearchCacheEntity(query)
        entity.time = System.currentTimeMillis()
        entity.repositories = repositories
        searchCacheDao.insert(entity)
    }

}