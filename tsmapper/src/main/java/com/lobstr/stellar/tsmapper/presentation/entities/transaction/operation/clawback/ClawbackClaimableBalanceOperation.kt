package com.lobstr.stellar.tsmapper.presentation.entities.transaction.operation.clawback

import android.content.Context
import android.os.Parcelable
import com.lobstr.stellar.tsmapper.R
import com.lobstr.stellar.tsmapper.presentation.entities.transaction.operation.Operation
import com.lobstr.stellar.tsmapper.presentation.entities.transaction.operation.OperationField
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClawbackClaimableBalanceOperation(
    override val sourceAccount: String?,
    val balanceId: String
) : Operation(sourceAccount), Parcelable {

    override fun getFields(context: Context, amountFormatter: (value: String) -> String): MutableList<OperationField> {
        val fields: MutableList<OperationField> = mutableListOf()
        fields.add(OperationField(context.getString(R.string.op_field_balance_id), balanceId))

        return fields
    }
}