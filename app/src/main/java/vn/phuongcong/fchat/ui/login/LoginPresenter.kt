package vn.phuongcong.fchat

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject


/**
 * Created by Ominext on 10/11/2017.
 */

class LoginPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                         var loginView: LoginView) {

    fun onSignIn(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                        loginView.onLoginSuccessfull();
                }
                .addOnFailureListener { exception: Exception ->
                    loginView.onError(exception.toString())
                }
    }

    fun onDestroy() {

    }

    fun checkEmailVerified() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user!!.isEmailVerified) {
            loginView.onVerified()
        } else {
            FirebaseAuth.getInstance().signOut()
            loginView.onViriFail()
        }
    }

}

