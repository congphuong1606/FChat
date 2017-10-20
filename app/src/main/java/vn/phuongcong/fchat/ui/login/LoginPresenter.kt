package vn.phuongcong.fchat

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

class LoginPresenter @Inject constructor(var fAuth: FirebaseAuth,
                                         var dbReference: DatabaseReference,
                                         var loginView: LoginView) {

    fun onSignIn(email: String, pass: String) {
        fAuth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    loginView.onLoginSuccessfull();
                }
                .addOnFailureListener { exception: Exception ->
                    loginView.onRequestFailure(exception.toString())
                }
    }


    fun checkEmailVerified() {
        val user = fAuth.currentUser
        if (user != null) {
            if (user.isEmailVerified) {
                getUserDatabase(user.uid)

            } else {
                fAuth.signOut()
                loginView.onViriFail()
            }
        }

    }

    private fun getUserDatabase(uid: String) {
        val databaseUser: DatabaseReference =
                dbReference.child(Contans.USERS_PATH).child(uid)
        databaseUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                loginView.onVerified(user)

            }

            override fun onCancelled(databaseError: DatabaseError) {
                loginView.onRequestFailure(databaseError.toString())
            }
        })
    }

}

