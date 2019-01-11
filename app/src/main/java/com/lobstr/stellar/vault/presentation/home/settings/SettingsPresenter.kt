package com.lobstr.stellar.vault.presentation.home.settings

import android.app.Activity
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.lobstr.stellar.vault.BuildConfig
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.domain.settings.SettingsInteractor
import com.lobstr.stellar.vault.presentation.BasePresenter
import com.lobstr.stellar.vault.presentation.application.LVApplication
import com.lobstr.stellar.vault.presentation.dagger.module.settings.SettingsModule
import com.lobstr.stellar.vault.presentation.util.Constant
import com.lobstr.stellar.vault.presentation.util.biometric.BiometricUtils
import javax.inject.Inject

@InjectViewState
class SettingsPresenter : BasePresenter<SettingsView>() {

    @Inject
    lateinit var interactor: SettingsInteractor

    init {
        LVApplication.sAppComponent.plusSettingsComponent(SettingsModule()).inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setupToolbarTitle(R.string.settings)
        viewState.setupSettingsData(
            interactor.getUserPublicKey(),
            BuildConfig.VERSION_NAME,
            BiometricUtils.isBiometricSupported(LVApplication.sAppComponent.context)
        )
        viewState.setTouchIdChecked(
            interactor.isTouchIdEnabled()
                    && BiometricUtils.isFingerprintAvailable(LVApplication.sAppComponent.context)
        )
    }

    override fun attachView(view: SettingsView?) {
        super.attachView(view)
        // always check signers count
        viewState.setupSignersCount(interactor.getSignersCount().toString())
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constant.Code.CHANGE_PIN -> viewState.showSuccessMessage(R.string.text_success_change_pin)
            }
        }
    }

    fun infoClicked() {
        viewState.showInfoFr()
    }

    fun logOutClicked() {
        interactor.clearUserData()
        viewState.showAuthScreen()
    }

    fun copyUserPublicKey(userPublicKey: String?) {
        if (userPublicKey.isNullOrEmpty()) {
            return
        }

        viewState.copyToClipBoard(userPublicKey)
    }

    fun signersClicked() {
        viewState.showSignersScreen()
    }

    fun mnemonicsClicked() {
        viewState.showMnemonicsScreen()
    }

    fun changePinClicked() {
        viewState.showChangePinScreen()
    }

    fun helpClicked() {
        viewState.showHelpScreen()
    }

    fun touchIdSwitched(checked: Boolean) {
        when {
            checked -> {
                if (BiometricUtils.isFingerprintAvailable(LVApplication.sAppComponent.context)) {
                    // TODO add additional logic if needed
                    interactor.setTouchIdEnabled(true)
                    viewState.setTouchIdChecked(true)
                } else {
                    interactor.setTouchIdEnabled(false)
                    viewState.setTouchIdChecked(false)
                    viewState.showFingerprintInfoDialog(
                        R.string.title_finger_print_dialog,
                        R.string.msg_finger_print_dialog
                    )
                }
            }
            else -> {
                interactor.setTouchIdEnabled(false)
            }
        }
    }
}