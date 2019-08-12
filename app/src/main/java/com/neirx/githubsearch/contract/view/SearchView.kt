package com.neirx.githubsearch.contract.view

import com.neirx.githubsearch.model.Repository

/**
 * Created by Waide Shery on 11.08.19.
 */
interface SearchView {
    fun onSearchResult(searchResult: List<Repository>)
    fun onSearchError(t: Throwable)
}