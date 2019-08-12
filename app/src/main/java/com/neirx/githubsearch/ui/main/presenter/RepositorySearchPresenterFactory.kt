package com.neirx.githubsearch.ui.main.presenter

import com.neirx.githubsearch.contract.presenter.SearchPresenter
import com.neirx.githubsearch.contract.presenter.SearchPresenterFactory
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.contract.view.SearchView
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Waide Shery on 12.08.19.
 */
class RepositorySearchPresenterFactory
@Inject
constructor(
    private val repositoryStorage: RepositoryStorage
) : SearchPresenterFactory {

    private val presenterMap = HashMap<String, RepositorySearchPresenter>()

    override fun get(searchView: SearchView): SearchPresenter {
        var simpleName = searchView::class.simpleName
        if (simpleName == null) simpleName = "simpleName"

        Timber.tag("RecreateTag").d("simpleName: $simpleName")
        var presenter = presenterMap[simpleName]
        if(presenter == null) {
            presenter = RepositorySearchPresenter(repositoryStorage)
            presenterMap[simpleName] = presenter
        } else{
            presenterMap.clear()
            presenterMap[simpleName] = presenter
        }
        presenter.attachView(searchView)

        return presenter
    }

}
