package com.lobstr.stellar.vault.presentation.auth.mnemonic.create_mnemonic

import com.arellomobile.mvp.InjectViewState
import com.lobstr.stellar.vault.domain.mnemonics.MnemonicsInteractor
import com.lobstr.stellar.vault.presentation.BasePresenter
import com.lobstr.stellar.vault.presentation.application.LVApplication
import com.lobstr.stellar.vault.presentation.dagger.module.mnemonics.MnemonicsModule
import com.lobstr.stellar.vault.presentation.entities.mnemonic.MnemonicItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class MnemonicsPresenter(private val generate: Boolean) : BasePresenter<MnemonicsView>() {

    @Inject
    lateinit var interactor: MnemonicsInteractor

    init {
        LVApplication.sAppComponent.plusMnemonicsComponent(MnemonicsModule()).inject(this)
    }

    private var mnemonicItemList: ArrayList<MnemonicItem>? = null

    private var mnemonicsStr: String? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (generate) {
            setupMnemonics(interactor.generate12WordMnemonics())
        } else {
//            viewState.setupToolbarTitle(R.string.mnemonics_title)
            viewState.setActionLayerVisibility(false)
            getExistingMnemonics()
        }
    }

    private fun getExistingMnemonics() {
        unsubscribeOnDestroy(
            interactor.getExistingMnemonics()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setupMnemonics(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun setupMnemonics(mnemonicItems: ArrayList<MnemonicItem>) {
        mnemonicsStr = mnemonicItems.joinToString(" ") { it -> it.value }

        mnemonicItemList = mnemonicItems
        viewState.setupMnemonics(mnemonicItems)
    }

    fun infoClicked() {
        viewState.showHelpScreen()
    }

    fun nextClicked() {
        viewState.showConfirmationScreen(mnemonicItemList!!)
    }

    fun clipToBordClicked() {
        if (mnemonicsStr.isNullOrEmpty()) {
            return
        }

        viewState.copyToClipBoard(mnemonicsStr!!)
    }
}