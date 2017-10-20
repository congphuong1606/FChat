package vn.phuongcong.fchat.ui.main.fragment.listmsg

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupView
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ListMsgPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                           var databaseReference: DatabaseReference,
                                           var listMsgView: ListMsgView){
}