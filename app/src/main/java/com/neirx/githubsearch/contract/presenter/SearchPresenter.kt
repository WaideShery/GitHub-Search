package com.neirx.githubsearch.contract.presenter

/**
 * Created by Waide Shery on 11.08.19.
 */
interface SearchPresenter {
    fun searchRepository(query : String)
    fun stopSearching()
    fun finish()
}