package vn.phuongcong.fchat.ui.splash

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import vn.phuongcong.fchat.R
import android.content.SharedPreferences
import vn.phuongcong.fchat.LoginActivity
import android.content.Intent
import android.content.Context.MODE_PRIVATE
import android.os.Handler
import vn.phuongcong.fchat.common.Contans


class SplashActivity : AppCompatActivity() {
    private var preferences: SharedPreferences? = null
    private var email: String? = null
    private var pass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        preferences = getSharedPreferences(Contans.SPF_NAME, Context.MODE_PRIVATE)
        email = preferences!!.getString(Contans.LOGIN_EMAIL, "")
        pass = preferences!!.getString(Contans.LOGIN_PASS, "")
        val handler = Handler()
        handler.postDelayed(Runnable {
            val i = Intent(this@SplashActivity, LoginActivity::class.java)
            i.putExtra("email", email)
            i.putExtra("pass", pass)
            i.putExtra("fromActivity", "splash")
            startActivity(i)
            finish()
        }, 3000)
    }
}
