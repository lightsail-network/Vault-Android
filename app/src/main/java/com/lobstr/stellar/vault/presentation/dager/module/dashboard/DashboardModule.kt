package com.lobstr.stellar.vault.presentation.dager.module.dashboard

import com.lobstr.stellar.vault.domain.dashboard.DashboardInteractor
import com.lobstr.stellar.vault.domain.dashboard.DashboardInteractorImpl
import com.lobstr.stellar.vault.domain.transaction.TransactionRepository
import com.lobstr.stellar.vault.presentation.dager.scope.HomeScope
import com.lobstr.stellar.vault.presentation.util.PrefsUtil
import dagger.Module
import dagger.Provides

@Module
class DashboardModule {

    @Provides
    @HomeScope
    internal fun provideDashboardInteractor(
        transactionRepository: TransactionRepository,
        prefsUtil: PrefsUtil
    ): DashboardInteractor {
        return DashboardInteractorImpl(transactionRepository, prefsUtil)
    }
}