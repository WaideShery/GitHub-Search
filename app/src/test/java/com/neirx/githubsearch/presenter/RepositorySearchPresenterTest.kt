package com.neirx.githubsearch.presenter

import com.neirx.githubsearch.contract.view.SearchView
import com.neirx.githubsearch.ui.main.presenter.RepositorySearchPresenter
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by Waide Shery on 12.08.19.
 */
@RunWith(MockitoJUnitRunner::class)
class RepositorySearchPresenterTest {
    @Mock
    private lateinit var mockSearchView: SearchView

    private var repositoryStorage = RepositorySearchStorage.get()

    private lateinit var presenter: RepositorySearchPresenter

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { t -> Schedulers.trampoline() }
        presenter = RepositorySearchPresenter(repositoryStorage)
        presenter.attachView(mockSearchView)
    }

    @After
    fun tearDown() {
        RxAndroidPlugins.reset()
    }

    @Test
    fun testRepositorySearchPresenter() {
        presenter.searchRepository("Test")
        Thread.sleep(500)
        verify(mockSearchView).onSearchResult(RepositorySearchStorage.repositories)
        presenter.searchRepository("")
        Thread.sleep(500)
        verify(mockSearchView).onSearchError(RepositorySearchStorage.throwable)
    }

}
