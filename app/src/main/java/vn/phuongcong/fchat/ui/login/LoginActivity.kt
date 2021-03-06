package vn.phuongcong.fchat


import android.app.ProgressDialog
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_login.*
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.CheckInput
import vn.phuongcong.fchat.common.utils.DialogUtils
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class LoginActivity : BaseActivity(), LoginView {
    override fun loginFail() {
        dialogUtils.hideLoading()
        if (!isFinishing) {
            DialogUtils.showErorr(this, "Tài khoản chưa được dăng ký, hoặc sai mật khẩu vui lòng kiểm tra lại")
        }
    }


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
            if (fromActivity == Contans.REGIS_ACTIVITY) {
                DialogUtils.showErorr(this, Contans.REQUEST_CHECK_EMAIL)
            } else {
                Remember.isChecked = pass!!.isNotEmpty()
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
                prefsEditor.putString(Contans.LOGIN_EMAIL, email)
                        .putString(Contans.LOGIN_PASS, pass)
                        .commit()

            } else {
                prefsEditor.clear().commit()
            }
            mPresenter.onSignIn(email!!, pass!!)
        }
    }


    override fun onRequestFailure(string: String) {


    }

    override fun showToast(msg: String) {

    }

    override fun onLoginSuccessfull() {
        mPresenter.checkEmailVerified()


    }

    override fun onVerified(user: User?) {
        dialogUtils.hideLoading()
        prefsEditor.putString(Contans.PRE_USER_ID, user!!.id)
                .putString(Contans.PRE_USER_EMAIL, user.email)
                .putString(Contans.PRE_USER_NAME, user.name)
                .putString(Contans.PRE_USER_AVATAR, user.avatar)
                .commit()
        onStartActivity(MainActivity::class.java)
        finish()
    }

    override fun onViriFail() {
        dialogUtils.hideLoading()
        DialogUtils.showErorr(this, Contans.VERY_FAIL)
    }


}
