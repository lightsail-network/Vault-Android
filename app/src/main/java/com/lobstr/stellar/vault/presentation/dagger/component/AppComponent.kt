package com.lobstr.stellar.vault.presentation.dagger.component

import android.content.Context
import com.lobstr.stellar.vault.domain.error.RxErrorUtils
import com.lobstr.stellar.vault.presentation.dagger.component.biometric.BiometricSetUpComponent
import com.lobstr.stellar.vault.presentation.dagger.component.config.ConfigComponent
import com.lobstr.stellar.vault.presentation.dagger.component.confirm_mnemonics.ConfirmMnemonicsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.dashboard.DashboardComponent
import com.lobstr.stellar.vault.presentation.dagger.component.fcm.FcmInternalComponent
import com.lobstr.stellar.vault.presentation.dagger.component.fcm.FcmServiceComponent
import com.lobstr.stellar.vault.presentation.dagger.component.home.HomeComponent
import com.lobstr.stellar.vault.presentation.dagger.component.import_xdr.ImportXdrComponent
import com.lobstr.stellar.vault.presentation.dagger.component.mnemonics.MnemonicsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.operation_details.OperationDetailsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.pin.PinComponent
import com.lobstr.stellar.vault.presentation.dagger.component.rate_us.RateUsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.re_check_signer.RecheckSignerComponent
import com.lobstr.stellar.vault.presentation.dagger.component.recovery_key.RecoveryKeyComponent
import com.lobstr.stellar.vault.presentation.dagger.component.settings.SettingsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.signed_account.SignedAccountComponent
import com.lobstr.stellar.vault.presentation.dagger.component.signer_info.SignerInfoComponent
import com.lobstr.stellar.vault.presentation.dagger.component.splash.SplashComponent
import com.lobstr.stellar.vault.presentation.dagger.component.transaction.TransactionComponent
import com.lobstr.stellar.vault.presentation.dagger.component.transaction_details.TransactionDetailsComponent
import com.lobstr.stellar.vault.presentation.dagger.component.vault_auth.VaultAuthComponent
import com.lobstr.stellar.vault.presentation.dagger.module.ApiModule
import com.lobstr.stellar.vault.presentation.dagger.module.AppModule
import com.lobstr.stellar.vault.presentation.dagger.module.RepositoryModule
import com.lobstr.stellar.vault.presentation.dagger.module.biometric.BiometricSetUpModule
import com.lobstr.stellar.vault.presentation.dagger.module.config.ConfigModule
import com.lobstr.stellar.vault.presentation.dagger.module.confirm_mnemonics.ConfirmMnemonicsModule
import com.lobstr.stellar.vault.presentation.dagger.module.dashboard.DashboardModule
import com.lobstr.stellar.vault.presentation.dagger.module.fcm.FcmInternalModule
import com.lobstr.stellar.vault.presentation.dagger.module.fcm.FcmServiceModule
import com.lobstr.stellar.vault.presentation.dagger.module.home.HomeModule
import com.lobstr.stellar.vault.presentation.dagger.module.import_xdr.ImportXdrModule
import com.lobstr.stellar.vault.presentation.dagger.module.mnemonics.MnemonicsModule
import com.lobstr.stellar.vault.presentation.dagger.module.operation_details.OperationDetailsModule
import com.lobstr.stellar.vault.presentation.dagger.module.pin.PinModule
import com.lobstr.stellar.vault.presentation.dagger.module.rate_us.RateUsModule
import com.lobstr.stellar.vault.presentation.dagger.module.re_check_signer.RecheckSignerModule
import com.lobstr.stellar.vault.presentation.dagger.module.recovery_key.RecoveryKeyModule
import com.lobstr.stellar.vault.presentation.dagger.module.settings.SettingsModule
import com.lobstr.stellar.vault.presentation.dagger.module.signed_account.SignedAccountModule
import com.lobstr.stellar.vault.presentation.dagger.module.signer_info.SignerInfoModule
import com.lobstr.stellar.vault.presentation.dagger.module.splash.SplashModule
import com.lobstr.stellar.vault.presentation.dagger.module.transaction.TransactionModule
import com.lobstr.stellar.vault.presentation.dagger.module.transaction_details.TransactionDetailsModule
import com.lobstr.stellar.vault.presentation.dagger.module.vault_auth.VaultAuthModule
import com.lobstr.stellar.vault.presentation.util.manager.network.NetworkWorker
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, RepositoryModule::class])
interface AppComponent {

    val context: Context

    val rxErrorUtils: RxErrorUtils

    fun plusSplashComponent(module: SplashModule): SplashComponent

    fun plusRecoveryKeyComponent(module: RecoveryKeyModule): RecoveryKeyComponent

    fun plusMnemonicsComponent(module: MnemonicsModule): MnemonicsComponent

    fun plusConfirmMnemonicsComponent(module: ConfirmMnemonicsModule): ConfirmMnemonicsComponent

    fun plusPinComponent(module: PinModule): PinComponent

    fun plusBiometricSetUpComponent(module: BiometricSetUpModule): BiometricSetUpComponent

    fun plusVaultAuthComponent(module: VaultAuthModule): VaultAuthComponent

    fun plusSignerInfoComponent(module: SignerInfoModule): SignerInfoComponent

    fun plusRecheckSignerComponent(module: RecheckSignerModule): RecheckSignerComponent

    fun plusHomeComponent(module: HomeModule): HomeComponent

    fun plusSettingsComponent(module: SettingsModule): SettingsComponent

    fun plusFcmServiceComponent(module: FcmServiceModule): FcmServiceComponent

    fun plusFcmInternalComponent(module: FcmInternalModule): FcmInternalComponent

    fun plusTransactionDetailsComponent(module: TransactionDetailsModule): TransactionDetailsComponent

    fun plusTransactionComponent(module: TransactionModule): TransactionComponent

    fun plusDashboardComponent(module: DashboardModule): DashboardComponent

    fun plusOperationDetailsComponent(module: OperationDetailsModule): OperationDetailsComponent

    fun plusSignedAccountComponent(module: SignedAccountModule): SignedAccountComponent

    fun plusImportXdrComponent(module: ImportXdrModule): ImportXdrComponent

    fun plusRateUsComponent(module: RateUsModule): RateUsComponent

    fun plusConfigComponent(module: ConfigModule): ConfigComponent

    fun inject(networkWorker: NetworkWorker)
}