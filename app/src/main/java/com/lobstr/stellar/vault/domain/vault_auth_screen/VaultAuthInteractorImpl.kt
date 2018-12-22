package com.lobstr.stellar.vault.domain.vault_auth_screen

import com.lobstr.stellar.vault.domain.key_store.KeyStoreRepository
import com.lobstr.stellar.vault.domain.stellar.StellarRepository
import com.lobstr.stellar.vault.domain.vault_auth.VaultAuthRepository
import com.lobstr.stellar.vault.presentation.application.LVApplication
import com.lobstr.stellar.vault.presentation.dager.module.fcm.FcmInternalModule
import com.lobstr.stellar.vault.presentation.fcm.FcmHelper
import com.lobstr.stellar.vault.presentation.util.PrefsUtil
import io.reactivex.Single
import javax.inject.Inject


class VaultAuthInteractorImpl(
    private val vaultAuthRepository: VaultAuthRepository,
    private val stellarRepository: StellarRepository,
    private val keyStoreRepository: KeyStoreRepository,
    private val prefsUtil: PrefsUtil
) : VaultAuthInteractor {

    @Inject
    lateinit var mFcmHelper: FcmHelper

    init {
        LVApplication.sAppComponent.plusFcmInternalComponent(FcmInternalModule()).inject(this)
    }

    override fun isUserAuthorized(): Boolean {
        return !prefsUtil.authToken.isNullOrEmpty()
    }

    override fun authorizeVault(): Single<String> {
        return getChallenge()
            .flatMap { transaction ->
                getPhrases().flatMap { stellarRepository.createKeyPair(it.toCharArray(), 0) }
                    .flatMap { stellarRepository.signTransaction(it, transaction) }
            }
            .flatMap { submitChallenge(it) }
            .doOnSuccess { prefsUtil.authToken = it }
            .doOnSuccess { registerFcm() }
    }

    override fun registerFcm() {
        mFcmHelper.checkIfFcmRegisteredSuccessfully()
    }

    override fun getChallenge(): Single<String> {
        return vaultAuthRepository.getChallenge(getUserPublicKey()!!)
    }

    override fun submitChallenge(transaction: String): Single<String> {
        return vaultAuthRepository.submitChallenge(transaction)
    }

    override fun getUserPublicKey(): String? {
        return prefsUtil.publicKey
    }

    override fun getPhrases(): Single<String> {
        return Single.fromCallable {
            return@fromCallable keyStoreRepository.decryptData(
                PrefsUtil.PREF_ENCRYPTED_PHRASES,
                PrefsUtil.PREF_PHRASES_IV
            )
        }
    }

    override fun confirmIsUserSignerForLobstr() {
        prefsUtil.isUserSignerForLobstr = true
    }
}