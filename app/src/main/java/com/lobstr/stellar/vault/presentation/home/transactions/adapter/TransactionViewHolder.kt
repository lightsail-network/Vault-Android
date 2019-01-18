package com.lobstr.stellar.vault.presentation.home.transactions.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.presentation.entities.transaction.Transaction
import com.lobstr.stellar.vault.presentation.entities.transaction.TransactionItem
import com.lobstr.stellar.vault.presentation.util.AppUtil
import kotlinx.android.synthetic.main.adapter_item_transaction.view.*
import org.joda.time.DateTime

class TransactionViewHolder(itemView: View, private val listener: OnTransactionItemClicked) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(item: TransactionItem) {
        val context: Context = itemView.context

        itemView.tvTransactionItemDate.text = AppUtil.formatDate(
            DateTime(item.addedAt).toDate().time,
            "MMM dd yyyy hh:mm a"
        )
        itemView.tvTransactionItemOperation.text = getOperationName(item.transaction, context)
        itemView.setOnClickListener {
            val position = this@TransactionViewHolder.adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }

            listener.onTransactionItemClick(item)
        }
    }

    private fun getOperationName(transaction: Transaction, context: Context): String {
        return if (transaction.operations.isNotEmpty()) {
            if (transaction.operations.size == 1) {
                context.getString(AppUtil.getTransactionOperationName(transaction.operations[0]))
            } else {
                String.format(
                    context.getString(R.string.text_operation_name_several_operation),
                    transaction.operations.size
                )
            }
        } else {
            context.getString(R.string.text_operation_name_unknown)
        }
    }
}