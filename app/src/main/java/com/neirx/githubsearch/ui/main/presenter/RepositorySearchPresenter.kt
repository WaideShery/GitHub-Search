package com.neirx.githubsearch.ui.main.presenter

import com.neirx.githubsearch.contract.presenter.SearchPresenter
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.contract.view.SearchView
import com.neirx.githubsearch.model.Repository
import com.neirx.githubsearch.ui.base.presenter.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Waide Shery on 11.08.19.
 */
class RepositorySearchPresenter(repositoryStorage: RepositoryStorage) : Presenter<SearchView>(), SearchPresenter {
    var gitHubStorage = repositoryStorage

    init {
        Timber.tag("RecreateTag").d("Create SearchPresenter: %s", this)
    }

    override fun searchRepository(query: String) {
        Timber.tag("RecreateTag").d("searchRepository, query: $query")
        addDisposable(gitHubStorage.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onSearchSuccess(it) }, { onSearchError(it) })
        )
    }

    private fun onSearchSuccess(searchResult: List<Repository>) {
        Timber.tag("RecreateTag").d("onSearchSuccess")
        runInView(Runnable { view()?.onSearchResult(searchResult) })
    }

    private fun onSearchError(t: Throwable) {
        Timber.tag("RecreateTag").d("onSearchError")
        runInView(Runnable { view()?.onSearchError(t) })
    }

    override fun stopSearching() {
        disposeAll()
    }

    override fun finish() {
        disposeAll()
    }

}
