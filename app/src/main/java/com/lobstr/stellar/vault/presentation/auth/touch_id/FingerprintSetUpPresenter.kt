package com.lobstr.stellar.vault.presentation.auth.touch_id

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.domain.touch_id.FingerprintSetUpInteractor
import com.lobstr.stellar.vault.presentation.application.LVApplication
import com.lobstr.stellar.vault.presentation.dagger.module.touch_id.FingerprintSetUpModule
import com.lobstr.stellar.vault.presentation.util.biometric.BiometricUtils
import javax.inject.Inject

@InjectViewState
class FingerprintSetUpPresenter : MvpPresenter<FingerprintSetUpView>() {

    @Inject
    lateinit var interactor: FingerprintSetUpInteractor

    init {
        LVApplication.sAppComponent.plusFingerprintSetUpComponent(FingerprintSetUpModule()).inject(this)
    }

    fun skipClicked() {
        viewState.showVaultAuthScreen()
    }

    fun turnOnClicked() {
        if (BiometricUtils.isFingerprintAvailable(LVApplication.sAppComponent.context)) {
            viewState.showBiometricDialog()
        } else {
            viewState.showFingerprintInfoDialog(
                R.string.title_finger_print_dialog,
                R.string.msg_finger_print_dialog
            )
        }
    }

    fun biometricAuthenticationSuccessful() {
        interactor.setTouchIdEnabled(true)
        viewState.showVaultAuthScreen()
    }
}