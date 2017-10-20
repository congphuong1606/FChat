package vn.phuongcong.fchat.ui.main.fragment.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ChatPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                       var databaseReference: DatabaseReference) {
}