package com.lobstr.stellar.vault.domain.vault_auth

import io.reactivex.Single


interface VaultAuthRepository {

    fun getChallenge(account: String?): Single<String>

    fun submitChallenge(transaction: String): Single<String>
}