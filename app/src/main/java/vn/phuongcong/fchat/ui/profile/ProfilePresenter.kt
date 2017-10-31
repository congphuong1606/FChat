package vn.phuongcong.fchat.ui.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ProfilePresenter @Inject constructor(var fAuth: FirebaseAuth,
                                           var profileView: ProfileView,
                                           var dbReference: DatabaseReference) {
    fun onSignOut(){
        fAuth.signOut()
        profileView.onSignOutSuccessful()
    }
}