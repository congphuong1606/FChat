package vn.phuongcong.fchat.ui.chat

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.data.Friend
import vn.phuongcong.fchat.data.User
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ChatPresenter @Inject constructor(var dbReference: DatabaseReference,
                                        var sPref: SharedPreferences,
                                        var chatView: ChatView) {
    fun onLoadListMsg(friendChat: User) {
        var uid = sPref.getString(Contans.PRE_USER_ID, "")
        var idChat = uid + friendChat.id


    }
}