package com.lobstr.stellar.vault.presentation.entities.transaction.operation

import android.os.Parcelable
import com.lobstr.stellar.vault.presentation.entities.transaction.Asset
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreatePassiveOfferOperation(
    override val sourceAccount: String?,
    val selling: Asset,
    val buying: Asset,
    val amount: String,
    val price: String
) : Operation(sourceAccount), Parcelable {

    fun getFieldsMap(): Map<String, String?> {
        val map: MutableMap<String, String?> = mutableMapOf()
        map["selling"] = selling.assetCode
        map["buying"] = buying.assetCode
        map["amount"] = amount
        map["price"] = price

        return map
    }
}