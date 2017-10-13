package vn.phuongcong.fchat.ui.regis

import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.child
import android.R.attr.name
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.User


/**
 * Created by Ominext on 10/13/2017.
 */
class RegisPresenter @Inject constructor(var regisView: RegisView,
                                         var firebaseAuth: FirebaseAuth,
                                         var databaseReference: DatabaseReference){
    fun onSignUp(email:String,pass:String ){
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener{task: Task<AuthResult> ->
                    if(task.isSuccessful){
                        val firebaseUser:FirebaseUser=task.result.user
                       firebaseUser?.let { firebaseUser ->
                           sendVeriEmail(firebaseUser)

                       }
                    }
                }
                .addOnFailureListener{exception ->
                    regisView.onError(exception.toString())
                }
    }

    private fun sendVeriEmail(firebaseUser: FirebaseUser) {
        firebaseUser.sendEmailVerification().addOnSuccessListener {
            regisView.onSignUpSuccessful()
        }
    }

    fun onCreatUserDatabase(email: String, pass: String) {
        val id = firebaseAuth.currentUser!!.uid
        val userName = email.split("@".toRegex()).toString()
        val currentUser = User(id, userName, email, pass)
        databaseReference.child(Contans.USERS_PATH).child(id).setValue(currentUser).addOnSuccessListener {
            regisView.onCreateUserSuccessful();
        }

    }
}