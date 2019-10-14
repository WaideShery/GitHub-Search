package com.neirx.githubsearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neirx.githubsearch.contract.repository.RepositoryStorage
import com.neirx.githubsearch.data.repository.base.Loadable
import com.neirx.githubsearch.data.repository.base.State
import com.neirx.githubsearch.model.Repository
import com.neirx.githubsearch.ui.base.view_model.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by Waide Shery on 14.10.19.
 */
class MainViewModel(private val gitHubStorage: RepositoryStorage) : BaseViewModel(){
    class Factory(val storage: RepositoryStorage) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(storage) as T
        }
    }

    private val repositoriesMutable = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = repositoriesMutable
    private val errorMutable = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = errorMutable
    private val loadingMutable = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = loadingMutable

    fun searchRepository(query: String) {
        errorMutable.value = null
        repositoriesMutable.value = emptyList()
        Timber.tag("RecreateTag").d("searchRepository, query: $query")
        addDisposable(gitHubStorage.search(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ handleSearching(it) }, { onSearchError(it) })
        )
    }

    private fun handleSearching(loadable: Loadable<List<Repository>>) {
        when(loadable.state){
            State.LOADING ->{
                loadingMutable.value = true
            }
            State.COMPLETED -> {
                loadingMutable.value = false
                repositoriesMutable.value = loadable.data
            }
        }
    }

    private fun onSearchError(t: Throwable) {
        loadingMutable.value = false
        errorMutable.value = t
    }

    fun stopSearching() {
        disposeAll()
    }

}