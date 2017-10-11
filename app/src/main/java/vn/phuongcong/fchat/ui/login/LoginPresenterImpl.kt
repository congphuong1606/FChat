package vn.phuongcong.fchat

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import vn.phuongcong.fchat.base.BasePresenter
import vn.phuongcong.fchat.ui.login.LoginPresenter
import vn.phuongcong.fchat.ui.login.LoginView
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Ominext on 10/11/2017.
 */
class LoginPresenterImpl @Inject constructor(var mAuth: FirebaseAuth) : LoginPresenter {

    private var loginView: LoginView? = null
    override fun attackView(loginView: LoginView) {
        this.loginView = loginView
    }


    override fun onSignIn(email: String, pass: String) {
        mAuth!!.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        loginView?.onLoginSuccessfull();
                    }
                }
                .addOnFailureListener { exception: Exception ->
                    loginView?.onError(exception.toString())
                }
    }

    override fun onDestroy() {

    }

}

