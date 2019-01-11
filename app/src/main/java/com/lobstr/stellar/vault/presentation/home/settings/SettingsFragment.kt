package com.lobstr.stellar.vault.presentation.home.settings

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.fusechain.digitalbits.util.manager.FragmentTransactionManager
import com.lobstr.stellar.vault.R
import com.lobstr.stellar.vault.presentation.auth.AuthActivity
import com.lobstr.stellar.vault.presentation.base.fragment.BaseFragment
import com.lobstr.stellar.vault.presentation.container.activity.ContainerActivity
import com.lobstr.stellar.vault.presentation.dialog.alert.base.AlertDialogFragment
import com.lobstr.stellar.vault.presentation.dialog.alert.base.AlertDialogFragment.DialogFragmentIdentifier.FINGERPRINT_INFO_DIALOG
import com.lobstr.stellar.vault.presentation.faq.FaqFragment
import com.lobstr.stellar.vault.presentation.pin.PinActivity
import com.lobstr.stellar.vault.presentation.signed_accounts.SignedAccountsFragment
import com.lobstr.stellar.vault.presentation.util.AppUtil
import com.lobstr.stellar.vault.presentation.util.Constant
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(), SettingsView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // ===========================================================
    // Constants
    // ===========================================================

    companion object {
        val LOG_TAG = SettingsFragment::class.simpleName
    }

    // ===========================================================
    // Fields
    // ===========================================================

    @InjectPresenter
    lateinit var mPresenter: SettingsPresenter

    private var mView: View? = null

    // ===========================================================
    // Constructors
    // ===========================================================

    @ProvidePresenter
    fun provideSettingsPresenter() = SettingsPresenter()

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
        mView = if (mView == null) inflater.inflate(R.layout.fragment_settings, container, false) else mView
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        tvLogOut.setOnClickListener(this)
        ivCopyUserPk.setOnClickListener(this)
        llSettingsSigners.setOnClickListener(this)
        llSettingsMnemonics.setOnClickListener(this)
        llSettingsChangePin.setOnClickListener(this)
        llSettingsHelp.setOnClickListener(this)
        swSettingsTouchId.setOnCheckedChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_info -> mPresenter.infoClicked()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        attachMvpDelegate()
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    // ===========================================================
    // Listeners, methods for/from Interfaces
    // ===========================================================

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvLogOut -> mPresenter.logOutClicked()
            R.id.ivCopyUserPk -> mPresenter.copyUserPublicKey(tvUserPublicKey.text.toString())
            R.id.llSettingsSigners -> mPresenter.signersClicked()
            R.id.llSettingsMnemonics -> mPresenter.mnemonicsClicked()
            R.id.llSettingsChangePin -> mPresenter.changePinClicked()
            R.id.llSettingsHelp -> mPresenter.helpClicked()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.swSettingsTouchId -> mPresenter.touchIdSwitched(isChecked)
        }
    }

    override fun setupToolbarTitle(titleRes: Int) {
        saveActionBarTitle(titleRes)
    }

    override fun setupSettingsData(
        userPublicKey: String?,
        buildVersion: String,
        isBiometricSupported: Boolean
    ) {
        llSettingsTouchId.visibility = if (isBiometricSupported) View.VISIBLE else View.GONE
        tvUserPublicKey.text = userPublicKey
        tvSettingsVersion.text = buildVersion
    }

    override fun setupSignersCount(signersCount: String) {
        tvSettingsSigners.text = String.format(getString(R.string.text_settings_signers), signersCount)
    }

    override fun copyToClipBoard(text: String) {
        AppUtil.copyToClipboard(context, text)
    }

    override fun showSuccessMessage(@StringRes message: Int) {
        Toast.makeText(context, getString(message), Toast.LENGTH_SHORT).show()
    }

    override fun showInfoFr() {
        FragmentTransactionManager.displayFragment(
            parentFragment!!.childFragmentManager,
            Fragment.instantiate(context, FaqFragment::class.java.name),
            R.id.fl_container,
            true
        )
    }

    override fun showAuthScreen() {
        val intent = Intent(context, AuthActivity::class.java)
        intent.putExtra(Constant.Extra.EXTRA_NAVIGATION_FR, Constant.Navigation.AUTH)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showSignersScreen() {
        FragmentTransactionManager.displayFragment(
            parentFragment!!.childFragmentManager,
            Fragment.instantiate(context, SignedAccountsFragment::class.java.name),
            R.id.fl_container,
            true
        )
    }

    override fun showMnemonicsScreen() {
        val intent = Intent(context, ContainerActivity::class.java)
        intent.putExtra(Constant.Extra.EXTRA_NAVIGATION_FR, Constant.Navigation.MNEMONICS)
        startActivity(intent)
    }

    override fun showChangePinScreen() {
        val intent = Intent(context, PinActivity::class.java)
        intent.putExtra(Constant.Extra.EXTRA_CHANGE_PIN, true)
        startActivityForResult(intent, Constant.Code.CHANGE_PIN)
    }

    override fun showHelpScreen() {
        FragmentTransactionManager.displayFragment(
            parentFragment!!.childFragmentManager,
            Fragment.instantiate(context, FaqFragment::class.java.name),
            R.id.fl_container,
            true
        )
    }

    override fun setTouchIdChecked(checked: Boolean) {
        swSettingsTouchId.setOnCheckedChangeListener(null)
        swSettingsTouchId.isChecked = checked
        swSettingsTouchId.setOnCheckedChangeListener(this)
    }

    override fun showFingerprintInfoDialog(titleRes: Int, messageRes: Int) {
        AlertDialogFragment.Builder(true)
            .setCancelable(true)
            .setTitle(getString(titleRes))
            .setMessage(getString(messageRes))
            .setPositiveBtnText(getString(R.string.text_btn_ok))
            .create()
            .show(childFragmentManager, FINGERPRINT_INFO_DIALOG)
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
