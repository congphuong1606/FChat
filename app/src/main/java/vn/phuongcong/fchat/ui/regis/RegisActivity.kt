package vn.phuongcong.fchat

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_regis.*
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.regis.RegisPresenter
import vn.phuongcong.fchat.ui.regis.RegisView
import vn.phuongcong.fchat.utils.CheckInput
import vn.phuongcong.fchat.utils.DialogUtils
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject

class RegisActivity : BaseActivity(), RegisView {


    private lateinit var email: String
    private lateinit var pass: String
    private lateinit var dialogUtils: DialogUtils
    var dialog: ProgressDialog?=null
    @Inject
    lateinit var regisPresenter: RegisPresenter

    override val contentLayoutID: Int
        get() = R.layout.activity_regis

    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData() {
        dialogUtils = DialogUtils(dialog, this)

    }

    override fun onClick() {
        btnSignUp.setOnClickListener {
            SignUpAction()
        }
        btnRegisBack.setOnClickListener {
            onStartActivity(LoginActivity::class.java)
            finish() }

    }

    private fun SignUpAction() {
        if (CheckInput.checkInPutRegis(
                edtRegisEmail, edtRegisPass,
                edtConfirmPass, this)) {
            dialogUtils.showLoading()
            email = edtRegisEmail.text.toString().trim()
            pass = edtRegisPass.text.toString().trim()
            regisPresenter.onSignUp(email, pass)
        }
    }

    override fun onError(string: String) {
        dialogUtils.hideLoading()
        if(string.equals(Contans.ERROR_ACOUNT_EXISTED)){
            DialogUtils.showErorr(this, Contans.ACOUNT_EXISTED)
        }else{
            DialogUtils.showErorr(this, string)
        }

    }

    override fun showToast(msg: String) {

    }

    override fun onSignUpSuccessful() {
        regisPresenter.onCreatUserDatabase(email, pass)
    }
    override fun onCreateUserSuccessful() {
        dialogUtils.hideLoading()
        intent= Intent(this,LoginActivity::class.java)
        intent.putExtra("email",email)
        intent.putExtra("pass",pass)
        intent.putExtra("fromActivity",Contans.REGIS_ACTIVITY)
        startActivity(intent)
        finish()
    }

}
