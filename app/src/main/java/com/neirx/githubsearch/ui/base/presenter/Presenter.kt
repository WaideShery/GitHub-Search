package com.neirx.githubsearch.ui.base.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber

/**
 * Created by Waide Shery on 11.08.19.
 */
abstract class Presenter<V> : LifecycleObserver {
    private val runnables: MutableList<Runnable> = ArrayList()
    private val disposableBag = CompositeDisposable()
    private var view: V? = null
    private var lifecycle: Lifecycle? = null
    protected var lastRunnable: Runnable? = null

    protected fun view(): V? {
        return view
    }

    fun attachView(view: V) {
        Timber.tag("RecreateTag").d("attachView: $view")
        this.view = view
        if (view is LifecycleOwner) {
            lifecycle = view.lifecycle
            lifecycle?.addObserver(this)
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        disposableBag.add(disposable)
    }

    protected fun disposeAll() {
        disposableBag.dispose()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Timber.tag("RecreateTag").d("Lifecycle.Event.ON_DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Timber.tag("RecreateTag").d("Lifecycle.Event.ON_CREATE")
        if (runnables.isNotEmpty()) {
            runRunnables()
        } else {
            Timber.tag("RecreateTag").d("lastRunnable = $lastRunnable")
            lastRunnable?.run()
        }
    }

    private fun runRunnables() {
        Timber.tag("RecreateTag").d("runRunnables()")
        for (runnable in runnables) runnable.run()
        runnables.clear()
    }

    protected fun runInView(runnable: Runnable) {
        lastRunnable = runnable
        Timber.tag("RecreateTag").d("currentState: %s", lifecycle?.currentState)
        if (lifecycle == null || lifecycle!!.currentState != Lifecycle.State.DESTROYED) {
            runnable.run()
        } else {
            addRunnable(runnable)
        }
    }

    private fun addRunnable(runnable: Runnable) {
        runnables.add(runnable)
    }
}