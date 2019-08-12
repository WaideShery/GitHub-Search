package com.neirx.githubsearch.contract.presenter

import com.neirx.githubsearch.contract.view.SearchView

/**
 * Created by Waide Shery on 12.08.19.
 */
interface SearchPresenterFactory {
    fun get(searchView: SearchView): SearchPresenter
}
