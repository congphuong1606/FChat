package vn.phuongcong.fchat.ui.main.fragment.chat

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.utils.DateTimeUltil
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ChatPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                       var databaseReference: DatabaseReference,
                                        var spf :SharedPreferences,
                                        var chatView : ChatView) {
    var uid=spf.getString(Contans.PRE_USER_ID,"")

    fun getListChat(mChatItem: Chat) {

        if(uid!=""&& mChatItem!=null){

            databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).addValueEventListener(object : ValueEventListener{
                override fun onCancelled(dataError: DatabaseError?) {

                }

                override fun onDataChange(data: DataSnapshot?) {
                //    messages.clear()
                    var messages : MutableList<Message> = mutableListOf()
                    for (children in data!!.children){
                    messages.add(children.getValue(Message :: class.java)!!)
                    }
                    chatView.getListMessageSuccess(messages)
                }
            })

               databaseReference.child(Contans.CHAT).child(mChatItem.uIdFriend).child(uid).addValueEventListener(object : ValueEventListener{
                           override fun onCancelled(databaseError: DatabaseError?) {

                           }
                           override fun onDataChange(dataSnapshot: DataSnapshot?) {
                               var messages : MutableList<Message> = mutableListOf()
                               for (children in dataSnapshot!!.children) {
                                   messages.add(children.getValue(Message::class.java)!!)
                               }
                               chatView.getListMessageReceiverSuccess(messages)
                               }

                       })
        }
    }

    fun sendMessagetext(messagetext: String, mChatItem: Chat) {

        var message =Message(uid,messagetext,"",DateTimeUltil.getTimeCurrent())
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)

    }
}