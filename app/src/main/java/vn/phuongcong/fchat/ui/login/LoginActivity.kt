package vn.phuongcong.fchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import vn.phuongcong.fchat.ui.login.LoginPresenter
import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject

class LoginActivity : AppCompatActivity(),LoginView {
    @Inject
    lateinit var mPresenter:LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        (myApp as MyApp).appComponent.injectTo(this)


    }
    override fun onError(string: String) {

    }

    override fun showToast(msg: String) {

    }

    override fun onLoginSuccessfull() {

    }


}
