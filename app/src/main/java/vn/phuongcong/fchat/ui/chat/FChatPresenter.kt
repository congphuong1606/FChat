package vn.phuongcong.fchat.ui.chat

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.model.FChat
import vn.phuongcong.fchat.ui.main.fragment.chat.ChatView
import javax.inject.Inject

/**
 * Created by Ominext on 11/2/2017.
 */
class FChatPresenter  @Inject constructor(var mAuth: FirebaseAuth,
                                          var databaseReference: DatabaseReference,
                                          var spf: SharedPreferences,
                                          var fChatView: FChatView){
    fun loadDataRoomchat(idRoomChat: String?) {
    }

    fun sendchat(idRoomChat: String?, fchat: FChat) {

    }
}