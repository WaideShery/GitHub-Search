package com.neirx.githubsearch.data.repository.base

/**
 * Created by Waide Shery on 13.10.19.
 */
data class Loadable<ResultType>(var state: State, var data: ResultType? = null) {

    companion object {
        /**
         * Creates [Loadable] object with `COMPLETED` state and [data].
         */
        fun <ResultType> complete(data: ResultType): Loadable<ResultType> = Loadable(State.COMPLETED, data)

        /**
         * Creates [Loadable] object with `LOADING` state to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): Loadable<ResultType> = Loadable(State.LOADING)
    }
}