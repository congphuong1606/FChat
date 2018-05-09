package vn.phuongcong.fchat.ui.main

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.ui.profile.ProfileView
import javax.inject.Inject

/**
 * Created by Ominext on 11/1/2017.
 */
class MainPresenter @Inject constructor(var fAuth: FirebaseAuth,
                                        var mainView: MainView,
                                        var storageReference: StorageReference,
                                        var sPref: SharedPreferences,
                                        var dbReference: DatabaseReference) {
    fun updateUserStatus() {

    }

    fun getUserInfor() {

    }
}