package com.lobstr.stellar.vault.presentation.home.transactions.operation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.presentation.base.fragment.BaseFragment
import com.lobstr.stellar.vault.presentation.home.transactions.operation.adapter.OperationDetailsAdapter
import com.lobstr.stellar.vault.presentation.util.Constant
import kotlinx.android.synthetic.main.fragment_operation_details.*

class OperationDetailsFragment : BaseFragment(), OperationDetailsView {

    // ===========================================================
    // Constants
    // ===========================================================

    companion object {
        val LOG_TAG = OperationDetailsFragment::class.simpleName
    }

    // ===========================================================
    // Fields
    // ===========================================================

    @InjectPresenter
    lateinit var mPresenter: OperationDetailsPresenter

    private var mView: View? = null

    // ===========================================================
    // Constructors
    // ===========================================================

    @ProvidePresenter
    fun provideOperationDetailsPresenter() = OperationDetailsPresenter(
        arguments?.getParcelable(Constant.Bundle.BUNDLE_TRANSACTION_ITEM)!!,
        arguments?.getInt(Constant.Bundle.BUNDLE_OPERATION_POSITION)!!
    )

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass
    // ===========================================================

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = if (mView == null) inflater.inflate(R.layout.fragment_operation_details, container, false) else mView
        return mView
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    override fun setupToolbarTitle(titleRes: Int) {
        saveActionBarTitle(titleRes)
    }

    override fun initRecycledView(map: Map<String, String?>) {
        rvOperationDetails.layoutManager = LinearLayoutManager(activity)
        rvOperationDetails.itemAnimator = null
        rvOperationDetails.adapter = OperationDetailsAdapter(map)
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}