package vn.phuongcong.fchat


import android.app.ProgressDialog
import android.content.SharedPreferences
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.User
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchat.utils.CheckInput
import vn.phuongcong.fchat.utils.DialogUtils

import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginView {


    private var email: String? = null
    private var pass: String? = null
    private lateinit var dialogUtils: DialogUtils
    private var dialog: ProgressDialog? = null

    @Inject
    lateinit var mPresenter: LoginPresenter
    @Inject
    lateinit var prefs: SharedPreferences
    @Inject
    lateinit var prefsEditor: SharedPreferences.Editor

    override val contentLayoutID: Int
        get() = R.layout.activity_login

    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)

    }

    override fun initData() {
        dialogUtils = DialogUtils(dialog, this)
        onGetIntent()
    }

    private fun onGetIntent() {
        email = getIntent().getStringExtra(Contans.KEY_EMAIL)
        pass = getIntent().getStringExtra(Contans.KEY_PASS)
        edtLoginEmail.setText(email)
        edtLoginPass.setText(pass)
        var fromActivity = getIntent().getStringExtra(Contans.KEY_FROM_ACTIVTY)
        fromActivity?.let {
            if (fromActivity.equals(Contans.REGIS_ACTIVITY)) {
                DialogUtils.showErorr(this, Contans.REQUEST_CHECK_EMAIL)
            }else{
                if(pass!!.length>0){
                    Remember.isChecked=true
                }else Remember.isChecked=false
            }
        }

    }

    override fun onClick() {
        btnLogin.setOnClickListener {

            loginAction()
        }
        btnRegis.setOnClickListener {
            onStartActivity(RegisActivity::class.java)
            finish()
        }
        btnQuit.setOnClickListener { finish() }
    }


    private fun loginAction() {
        if (CheckInput.checkInPutLogin(edtLoginEmail,
                edtLoginPass, this)) {
            dialogUtils.showLoading()
            email = edtLoginEmail.text.toString().trim();
            pass = edtLoginPass.text.toString().trim();
            if (Remember.isChecked) {
                prefsEditor.putString(Contans.LOGIN_EMAIL,email)
                        .putString(Contans.LOGIN_PASS, pass)
                        .commit()

            }else{
                prefsEditor.clear().commit()
            }
            mPresenter.onSignIn(email!!, pass!!)
        }
    }


    override fun onError(string: String) {
        dialogUtils.hideLoading()
        DialogUtils.showErorr(this, string)

    }

    override fun showToast(msg: String) {

    }

    override fun onLoginSuccessfull() {
        mPresenter.checkEmailVerified()


    }

    override fun onVerified(user: User?) {
        dialogUtils.hideLoading()
        App().UID=user!!.id
        App().UEMAIL=user!!.email
        App().UNAME=user!!.name
        App().UAVATAR=user!!.avatar
        onStartActivity(MainActivity::class.java)
        finish()
    }

    override fun onViriFail() {
        dialogUtils.hideLoading()
        DialogUtils.showErorr(this, Contans.VERY_FAIL)
    }


}
