package vn.phuongcong.fchat.ui.splash

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.model.User
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class SplashPresenter @Inject constructor(var fAuth: FirebaseAuth,
                                          var editor: SharedPreferences.Editor,
                                          var splashView: SplashView,
                                          var dbReference: DatabaseReference) {

    fun checkLogined(){
        val user = fAuth.currentUser
        if(user!=null ){
            if (user.isEmailVerified) {
                getUserDatabase(user.uid)
            } else {
                splashView.notLognIn()
            }
        }else  splashView.notLognIn()

    }

    private fun getUserDatabase(uid: String){
        val databaseUser: DatabaseReference =
                dbReference.child(Contans.USERS_PATH).child(uid)
        databaseUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user= dataSnapshot.getValue(User::class.java)
                if(user!=null){
                    setValue(user)
                    App().setInforUser(user)
                    splashView.onLognInEd()
                }
//
//                App().UEMAIL=user.email
//                App().UNAME=user.name
//                App().UAVATAR=user.avatar


            }
            override fun onCancelled(databaseError: DatabaseError) {
               splashView.notLognIn()
            }
        })

    }

    private fun setValue(user: User) {
        editor.putString(Contans.PRE_USER_ID,user.id)
                .putString(Contans.PRE_USER_EMAIL,user.email)
                .putString(Contans.PRE_USER_NAME,user.name)
                .putString(Contans.PRE_USER_AVATAR,user.avatar)
                .commit()
    }
}