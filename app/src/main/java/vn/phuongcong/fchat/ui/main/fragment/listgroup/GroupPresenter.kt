package vn.phuongcong.fchat.ui.main.fragment.listgroup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.ui.login.LoginView
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class GroupPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                         var databaseReference: DatabaseReference,
                                         var groupView: GroupView) {

}