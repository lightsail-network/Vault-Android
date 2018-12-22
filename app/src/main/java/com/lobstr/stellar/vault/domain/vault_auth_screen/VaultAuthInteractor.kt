package com.lobstr.stellar.vault.domain.vault_auth_screen

import io.reactivex.Single


interface VaultAuthInteractor {

    fun isUserAuthorized(): Boolean

    fun authorizeVault(): Single<String>

    fun registerFcm()

    fun getChallenge(): Single<String>

    fun submitChallenge(transaction: String): Single<String>

    fun getUserPublicKey(): String?

    fun getPhrases(): Single<String>

    fun confirmIsUserSignerForLobstr()
}