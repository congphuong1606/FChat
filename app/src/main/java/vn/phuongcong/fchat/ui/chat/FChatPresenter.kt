package vn.phuongcong.fchat.ui.chat

import android.content.SharedPreferences
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.FChat
import javax.inject.Inject

/**
 * Created by Ominext on 11/2/2017.
 */
class FChatPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                         var dbReference: DatabaseReference,
                                         var spf: SharedPreferences,
                                         var fChatView: FChatView) {
    fun loadDataRoomchat(idRoomChat: String?, chats: MutableList<FChat>) {
        dbReference.child(Contans.CHAT_PATH).child(idRoomChat).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                fChatView.onRequestFailure(error.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapTo(chats) {
                    it.getValue(FChat::class.java)!!
                }
                fChatView.onLoadChatsSuccess(chats)
            }


        })
    }

    fun sendchat(idRoomChat: String?, fchat: FChat) {
        dbReference.child(Contans.CHAT_PATH).child(idRoomChat).push().setValue(fchat)
                .addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        fChatView.onSendChatSuccessful(fchat)
                    } else {
                        fChatView.onRequestFailure(task.exception.toString())
                    }
                }
    }
}