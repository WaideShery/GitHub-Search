package com.neirx.githubsearch.data.repository.base

/**
 * Created by Waide Shery on 30.09.19.
 */
enum class State {
    COMPLETED,
    LOADING;

    /**
     * Returns `true` if the [State] is loading else `false`.
     */
    fun isLoading() = this == LOADING
}