package com.lobstr.stellar.vault.presentation.home.transactions.submit_success


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.presentation.base.fragment.BaseFragment
import com.lobstr.stellar.vault.presentation.util.AppUtil
import com.lobstr.stellar.vault.presentation.util.Constant
import kotlinx.android.synthetic.main.fragment_success.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class SuccessFragment : BaseFragment(), SuccessView, View.OnClickListener {

    // ===========================================================
    // Constants
    // ===========================================================

    companion object {
        val LOG_TAG = SuccessFragment::class.simpleName
    }

    // ===========================================================
    // Fields
    // ===========================================================

    @InjectPresenter
    lateinit var mPresenter: SuccessPresenter

    private var mView: View? = null

    // ===========================================================
    // Constructors
    // ===========================================================

    @ProvidePresenter
    fun provideSuccessPresenter() = SuccessPresenter(
        requireArguments().getString(Constant.Bundle.BUNDLE_ENVELOPE_XDR)!!,
        requireArguments().getBoolean(Constant.Bundle.BUNDLE_NEED_ADDITIONAL_SIGNATURES, false)
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
        mView = if (mView == null) inflater.inflate(R.layout.fragment_success, container, false) else mView
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        btnCopyXdr.setOnClickListener(this)
        btnDone.setOnClickListener(this)
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    override fun onClick(v: View?) {
        when (v?.id) {
            btnCopyXdr.id -> mPresenter.copyXdrClicked()
            btnDone.id -> mPresenter.doneClicked()
        }
    }

    override fun vibrate(pattern: LongArray) {
        AppUtil.vibrate(requireContext(), pattern)
    }

    override fun setupXdr(xdr: String) {
        tvXdr.text = xdr
    }

    override fun finishScreen() {
        activity?.onBackPressed()
    }

    override fun setAdditionalSignaturesInfoEnabled(enabled: Boolean) {
        tvAdditionalSignaturesDescription.visibility = if (enabled) View.VISIBLE else View.GONE
        llXdrContainer.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun copyToClipBoard(text: String) {
        AppUtil.copyToClipboard(context, text)
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
