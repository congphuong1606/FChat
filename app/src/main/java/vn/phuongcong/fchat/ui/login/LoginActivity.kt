package vn.phuongcong.fchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import vn.phuongcong.fchat.di.login.LoginModule
import vn.phuongcong.fchat.ui.login.LoginPresenter
import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {

    @Inject
    var loginPresenter: LoginPresenter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (application as MyApp).component.plus(LoginModule(this)).injectTo(this)


    }

    override fun onError(string: String) {

    }

    override fun showToast(msg: String) {

    }

    override fun onLoginSuccessfull() {

    }


}
