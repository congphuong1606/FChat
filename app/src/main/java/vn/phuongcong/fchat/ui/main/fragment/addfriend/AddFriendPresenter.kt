package vn.phuongcong.fchat.ui.main.fragment.addfriend

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.User
import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject

/**
 * Created by Ominext on 10/11/2017.
 */

class AddFriendPresenter @Inject constructor(var databaseReference: DatabaseReference,
                                             var addFriendView: AddFriendView) {




    fun onSearchUser(text: String) {

        text.trim()
    }

}