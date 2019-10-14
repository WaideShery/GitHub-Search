package com.neirx.githubsearch.ui.base.view_model

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Waide Shery on 09.10.19.
 */
open class BaseViewModel : ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    protected fun disposeAll() {
        compositeDisposable.dispose()
    }
}