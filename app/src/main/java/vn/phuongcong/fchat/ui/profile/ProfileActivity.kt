package vn.phuongcong.fchat.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import kotlinx.android.synthetic.main.activity_profile.*
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.LoginActivity
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchat.ui.splash.SplashActivity
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ProfileActivity : BaseActivity(), ProfileView {

    @Inject
    lateinit var mPresenter: ProfilePresenter


    override fun injectDependence() {
        (application as App).component
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun onSignOutSuccessful() {
        val i = Intent(this@ProfileActivity, SplashActivity::class.java)
        startActivity(i)
        MainActivity().finish()
        finish()
    }


    override val contentLayoutID: Int
        get() = R.layout.activity_profile

    override fun initData() {

    }

    override fun onClick() {
        btnSignOut.setOnClickListener {
            mPresenter.onSignOut()
        }

    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

}
