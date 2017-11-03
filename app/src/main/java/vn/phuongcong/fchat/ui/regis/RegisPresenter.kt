package vn.phuongcong.fchat.ui.regis

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.User
import android.widget.Toast
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.R.string.user


/**
 * Created by Ominext on 10/13/2017.
 */
class RegisPresenter @Inject constructor(var regisView: RegisView,
                                         var firebaseAuth: FirebaseAuth,
                                         var databaseReference: DatabaseReference) {
    fun onSignUp(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result.user
                        firebaseUser?.let { firebaseUser ->
                            sendVeriEmail(firebaseUser)

                        }
                    }
                }
                .addOnFailureListener { exception ->
                    regisView.onRequestFailure(exception.toString())
                }
    }

    private fun sendVeriEmail(firebaseUser: FirebaseUser) {

        val user = firebaseAuth.getCurrentUser()
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    regisView.onSignUpSuccessful()
                } else {
                    regisView.onRequestFailure("lỗi")
                }
            }
        }
    }


    fun onCreatUserDatabase(email: String, pass: String) {
        val id = firebaseAuth.currentUser!!.uid
        val userName = (email.split("@".toRegex()))[0]
        val currentUser = User(id, userName, email, "",System.currentTimeMillis())
        databaseReference.child(Contans.USERS_PATH).child(id).setValue(currentUser).addOnSuccessListener {
            regisView.onCreateUserSuccessful()
        }

    }
}