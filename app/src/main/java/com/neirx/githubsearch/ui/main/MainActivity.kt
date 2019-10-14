package com.neirx.githubsearch.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neirx.githubsearch.R
import com.neirx.githubsearch.model.Repository
import com.neirx.githubsearch.ui.base.utils.hideKeyboard
import com.neirx.githubsearch.ui.main.adapter.RepositoryAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), RepositoryAdapter.OnRepositoryClickListener {

    @Inject
    lateinit var viewModel: MainViewModel
    lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag("RecreateTag").d("onCreate: %s", this)
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repositoryAdapter = RepositoryAdapter()
        repositoryAdapter.onRepositoryClickListener = this
        rvRepositories.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        val dividerDrawable : Drawable? = ContextCompat.getDrawable(this, R.drawable.list_divider);
        if(dividerDrawable != null){
            divider.setDrawable(resources.getDrawable(R.drawable.list_divider))
        }
        rvRepositories.addItemDecoration(divider)
        rvRepositories.adapter = repositoryAdapter

        btnSearch.setOnClickListener { startSearch() }
        ivCancel.setOnClickListener { stopSearch() }

        setup()
    }

    private fun setup() {
        viewModel.loading.observe(this, Observer {
            if (it) showSearchProgress()
            else showSearchButton()
        })
        viewModel.repositories.observe(this, Observer {
            if (it != null) onSearchResult(it)
        })
        viewModel.error.observe(this, Observer {
            if(it != null) onSearchError(it)
        })
    }

    private fun startSearch() {
        hideKeyboard(this)
        val query = etSearch.text.toString()
        Timber.tag("RecreateTag").d("MainActivity.startSearch, query: $query")
        if (!query.isEmpty()) {
            viewModel.searchRepository(query)
        }
    }

    private fun showSearchProgress() {
        btnSearch.visibility = View.INVISIBLE
        btnSearch.isEnabled = false
        progressBar.visibility = View.VISIBLE
        ivCancel.visibility = View.VISIBLE
    }

    private fun stopSearch() {
        viewModel.stopSearching()
        showSearchButton()
    }

    private fun showSearchButton() {
        progressBar.visibility = View.GONE
        ivCancel.visibility = View.GONE
        btnSearch.visibility = View.VISIBLE
        btnSearch.isEnabled = true
    }

    private fun onSearchResult(searchResult: List<Repository>) {
        Timber.tag("RecreateTag").d("MainActivity.onSearchResult")
        showSearchButton()
        repositoryAdapter.setRepositories(searchResult)
    }

    private fun onSearchError(t: Throwable) {
        Timber.tag("RecreateTag").d("MainActivity.onSearchError")
        showSearchButton()
        Toast.makeText(this, t.localizedMessage, Toast.LENGTH_LONG).show()
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
