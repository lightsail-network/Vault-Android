package com.lobstr.stellar.vault.domain.transaction

import com.lobstr.stellar.vault.presentation.entities.transaction.TransactionResult
import io.reactivex.Single

interface TransactionInteractor {
    fun getTransactionList(nextPageUrl: String?): Single<TransactionResult>
    fun getPendingTransactionList(nextPageUrl: String?): Single<TransactionResult>
    fun getInactiveTransactionList(nextPageUrl: String?): Single<TransactionResult>
}