package vn.phuongcong.fchat.ui.main

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject

/**
 * Created by Ominext on 10/11/2017.
 */

class MainPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                        var databaseReference: DatabaseReference,
                                        var mainView: MainView) {

    fun onSearchUser(text: String) {


        text.trim()
    }

    fun onDestroy() {

    }

}