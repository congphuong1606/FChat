package vn.phuongcong.fchat.ui.splash

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import vn.phuongcong.fchat.R
import android.content.SharedPreferences
import vn.phuongcong.fchat.LoginActivity
import android.content.Intent
import android.os.Handler
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchat.ui.profile.ProfilePresenter
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject
import kotlin.coroutines.experimental.EmptyCoroutineContext.plus


class SplashActivity : BaseActivity(), SplashView {


    private var email: String? = null
    private var pass: String? = null

    @Inject
    lateinit var mPresenter: SplashPresenter
    @Inject
    lateinit var sharedPref: SharedPreferences

    override val contentLayoutID: Int
        get() = R.layout.activity_splash

    override fun onClick() {
    }

    override fun injectDependence() {
       App().get(this)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData() {
        mPresenter.checkLogined()
    }


    override fun notLognIn() {
        email = sharedPref!!.getString(Contans.LOGIN_EMAIL, "")
        pass = sharedPref!!.getString(Contans.LOGIN_PASS, "")
        val handler = Handler()
        handler.postDelayed(Runnable {
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            i.putExtra(Contans.KEY_EMAIL, email)
            i.putExtra(Contans.KEY_PASS, pass)
            i.putExtra(Contans.KEY_FROM_ACTIVTY, Contans.SPLASH_ACTIVITY)
            startActivity(i)
            finish()
        }, 2000)


    }

    override fun onLognInEd() {
        onStartActivity(MainActivity::class.java)
        finish()
    }
}

