package com.neirx.githubsearch.contract.repository

import com.neirx.githubsearch.model.Repository
import io.reactivex.Observable

/**
 * Created by Waide Shery on 11.08.19.
 */
interface RepositoryStorage {
    fun search(query: String): Observable<List<Repository>>
}
