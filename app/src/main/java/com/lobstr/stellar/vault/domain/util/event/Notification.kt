package com.lobstr.stellar.vault.domain.util.event

data class Notification(val type: Byte, val data: Any?) {
    object Type {
        const val SIGNED_NEW_ACCOUNT: Byte = 0
        const val ADDED_NEW_TRANSACTION: Byte = 1
    }
}