package vn.phuongcong.fchat

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import vn.phuongcong.fchat.data.User

import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.common.Contans


/**
 * Created by Ominext on 10/11/2017.
 */

class LoginPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                         var databaseReference: DatabaseReference,
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
            getUserDatabase(user.uid)

        } else {
            FirebaseAuth.getInstance().signOut()
            loginView.onViriFail()
        }
    }

    private fun getUserDatabase(uid: String) {
        val databaseUser:DatabaseReference =
                databaseReference.child(Contans.USERS_PATH).child(uid)
        databaseUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user= dataSnapshot.value

//                loginView.onVerified(user)

            }
            override fun onCancelled(databaseError: DatabaseError) {
               loginView.onError(databaseError.toString())
            }
        })
    }

}

