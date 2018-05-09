package vn.phuongcong.fchat.ui.profile

import android.content.SharedPreferences
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import vn.phuongcong.fchat.common.Contans
import javax.inject.Inject
import com.google.firebase.auth.EmailAuthProvider


/**
 * Created by Ominext on 10/18/2017.
 */
class ProfilePresenter @Inject constructor(var fAuth: FirebaseAuth,
                                           var profileView: ProfileView,
                                           var storageReference: StorageReference,
                                           var sPref: SharedPreferences,
                                           var dbReference: DatabaseReference) {
    fun onSignOut() {
        fAuth.signOut()
        profileView.onSignOutSuccessful()
    }

    fun changeAvatar(picByte: ByteArray) {
        val picName = System.currentTimeMillis().toString()
        val mSto = storageReference.child(Contans.IMAGE_PATH).child(picName + ".jpg")
        val uploadTask = mSto.putBytes(picByte)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            val avatarUrl = taskSnapshot.downloadUrl.toString()
            updateAvatarDatabase(avatarUrl)
        }.addOnFailureListener{ exception ->
            profileView.onRequestFailure(exception.toString())
        }
    }

    private fun updateAvatarDatabase(avatarUrl: String) {
        var Uid = sPref.getString(Contans.PRE_USER_ID, "")
        dbReference.child(Contans.USERS_PATH).child(Uid).child(Contans.ATATAR_PATH).setValue(avatarUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        profileView.onUpdateAvatarSuccessful(avatarUrl)

                    }
                }
                .addOnFailureListener { exception ->
            profileView.onRequestFailure(exception.toString())
        }
    }

    fun replaceAccName(text: String) {
        var Uid = sPref.getString(Contans.PRE_USER_ID, "")
        dbReference.child(Contans.USERS_PATH).child(Uid).child(Contans.NAME_PATH).setValue(text)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        profileView.onUpdateNameSuccessful(text)
                    }
                }
                .addOnFailureListener { exception ->
                    profileView.onRequestFailure(exception.toString())
                }
    }

    fun resetPassword(oldPass: String, newPass: String) {
        var user=fAuth.currentUser
        if(user!=null){
            var email=user.email
            val credential = EmailAuthProvider.getCredential(email!!, oldPass)
            user.reauthenticate(credential).addOnCompleteListener { task: Task<Void> ->
                if(task.isSuccessful){
                    user.updatePassword(newPass).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            profileView.onUpdatePassWordSuccessfull();
                        }else {
                            profileView.onRequestFailure(Contans.CHANG_PASS_NOT_FOUND)
                        }
                    }
                }else{
                    profileView.onRequestFailure("Authentication Failed")
                }
            }
        }

    }
}