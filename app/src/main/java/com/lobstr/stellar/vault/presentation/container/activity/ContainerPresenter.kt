package com.lobstr.stellar.vault.presentation.container.activity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lobstr.stellar.vault.presentation.entities.transaction.TransactionItem
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.DASHBOARD
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.MNEMONICS
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.SETTINGS
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.SUCCESS
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.TRANSACTIONS
import com.lobstr.stellar.vault.presentation.util.Constant.Navigation.TRANSACTION_DETAILS

@InjectViewState
class ContainerPresenter(
    private val targetFr: Int,
    private val transactionItem: TransactionItem?,
    private val envelopeXdr: String?,
    private val needAdditionalSignatures: Boolean?
) : MvpPresenter<ContainerView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        navigateTo()
    }

    private fun navigateTo() {
        when (targetFr) {
            DASHBOARD -> viewState.showDashBoardFr()

            TRANSACTIONS -> viewState.showTransactionsFr()

            SETTINGS -> viewState.showSettingsFr()

            TRANSACTION_DETAILS -> viewState.showTransactionDetails(transactionItem!!)

            MNEMONICS -> viewState.showMnemonicsFr()

            SUCCESS -> viewState.showSuccessFr(envelopeXdr!!, needAdditionalSignatures!!)
        }
    }

}