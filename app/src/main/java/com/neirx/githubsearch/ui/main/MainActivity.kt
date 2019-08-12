package com.neirx.githubsearch.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neirx.githubsearch.R
import com.neirx.githubsearch.contract.presenter.SearchPresenter
import com.neirx.githubsearch.contract.presenter.SearchPresenterFactory
import com.neirx.githubsearch.contract.view.SearchView
import com.neirx.githubsearch.model.Repository
import com.neirx.githubsearch.ui.base.utils.hideKeyboard
import com.neirx.githubsearch.ui.main.adapter.RepositoryAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), SearchView, RepositoryAdapter.OnRepositoryClickListener {

    @Inject
    lateinit var presenterFactory: SearchPresenterFactory
    lateinit var presenter: SearchPresenter
    lateinit var repositoryAdapter: RepositoryAdapter

    private var searchInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag("RecreateTag").d("onCreate: %s", this)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = presenterFactory.get(this)

        repositoryAdapter = RepositoryAdapter()
        repositoryAdapter.onRepositoryClickListener = this
        rvRepositories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.list_divider))
        rvRepositories.addItemDecoration(divider)
        rvRepositories.adapter = repositoryAdapter

        btnSearch.setOnClickListener { startSearch() }
        ivCancel.setOnClickListener { stopSearch() }

        if (savedInstanceState != null){
            searchInProgress = savedInstanceState.getBoolean("searchInProgress", false)
            if (searchInProgress) showSearchProgress()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean("searchInProgress", searchInProgress)
        super.onSaveInstanceState(outState)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putBoolean("searchInProgress", searchInProgress)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun startSearch() {
        hideKeyboard(this)
        val query = etSearch.text.toString()
        Timber.tag("RecreateTag").d("MainActivity.startSearch, query: $query")
        onSearchResult(emptyList())
        if (!query.isEmpty()) {
            showSearchProgress()
            presenter.searchRepository(query)
        }
    }

    private fun showSearchProgress() {
        searchInProgress = true
        btnSearch.visibility = View.INVISIBLE
        btnSearch.isEnabled = false
        progressBar.visibility = View.VISIBLE
        ivCancel.visibility = View.VISIBLE
    }

    private fun stopSearch() {
        presenter.stopSearching()
        showSearchButton()
    }

    private fun showSearchButton() {
        searchInProgress = false
        progressBar.visibility = View.GONE
        ivCancel.visibility = View.GONE
        btnSearch.visibility = View.VISIBLE
        btnSearch.isEnabled = true
    }

    override fun onSearchResult(searchResult: List<Repository>) {
        Timber.tag("RecreateTag").d("MainActivity.onSearchResult")
        showSearchButton()
        repositoryAdapter.setRepositories(searchResult)
    }

    override fun onSearchError(t: Throwable) {
        Timber.tag("RecreateTag").d("MainActivity.onSearchError")
        showSearchButton()
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        presenter.finish()
        super.onBackPressed()
    }

    override fun onRepositoryClick(repository: Repository) {
        val webPage = Uri.parse(repository.url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Browser not found", Toast.LENGTH_SHORT).show()
        }
    }

}
