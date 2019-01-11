package com.lobstr.stellar.vault.presentation

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.lobstr.stellar.vault.presentation.util.manager.network.NetworkWorker
import com.lobstr.stellar.vault.presentation.util.manager.network.WorkerManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*


open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val compositeDisposable = CompositeDisposable()
    protected var networkWorkerId: UUID? = null
    protected var needCheckConnectionState = false

    protected fun unsubscribeOnDestroy(disposable: Disposable?) {
        if (disposable == null) {
            return
        }

        compositeDisposable.add(disposable)
    }

    protected fun unsubscribeNow() {
        compositeDisposable.clear()
    }

    /**
     * Call it after internet connection fail
     */
    protected fun handleNoInternetConnection() {
        // Prevent creation several NetworkWorkers.
        if (networkWorkerId != null) {
            return
        }

        needCheckConnectionState = true
        networkWorkerId = WorkerManager.createNetworkStateWorker(NetworkWorker::class.java)
    }

    protected fun cancelNetworkWorker() {
        WorkerManager.cancelWorkById(networkWorkerId)
        networkWorkerId = null
    }

    /**
     * Recheck connection receiver
     */
    override fun attachView(view: View?) {
        if (needCheckConnectionState)
            handleNoInternetConnection()
        super.attachView(view)
    }

    /**
     * detach connection receiver
     */
    override fun detachView(view: View) {
        cancelNetworkWorker()
        super.detachView(view)
    }

    override fun onDestroy() {
        super.onDestroy()

        unsubscribeNow()
    }
}