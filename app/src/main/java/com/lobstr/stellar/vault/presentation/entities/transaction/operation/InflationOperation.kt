package com.lobstr.stellar.vault.presentation.entities.transaction.operation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InflationOperation(
    override val sourceAccount: String?
) : Operation(sourceAccount), Parcelable {

    fun getFieldsMap(): Map<String, String?> {
        return mutableMapOf()
    }
}