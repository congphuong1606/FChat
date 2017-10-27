package vn.phuongcong.fchat.ui.main.fragment.chat

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.utils.DateTimeUltil
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class ChatPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                        var databaseReference: DatabaseReference,
                                        var spf: SharedPreferences,
                                        var chatView: ChatView) {
    var uid = spf.getString(Contans.PRE_USER_ID, "")

    var messages: MutableList<Message> = mutableListOf()
    fun getListChat(mChatItem: Chat) {

        if (uid != "" && mChatItem != null) {

            databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {

                    var message: Message = dataSnapshot!!.getValue(Message::class.java)!!
                    message.mType = 0
                    messages.add(message)
                    chatView.getListMessageSuccess(messages)
                }

                override fun onChildRemoved(p0: DataSnapshot?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onCancelled(dataError: DatabaseError?) {
                }


            })

            databaseReference.child(Contans.CHAT).child(mChatItem.uIdFriend).child(uid).addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

                }

                override fun onChildRemoved(p0: DataSnapshot?) {

                }

                override fun onChildAdded(dataSnapshot: DataSnapshot?, p1: String?) {
                    var message: Message = dataSnapshot!!.getValue(Message::class.java)!!
                    message.mType = 1
                    messages.add(message)
                    chatView.getListMessageSuccess(messages)
                }

                override fun onCancelled(databaseError: DatabaseError?) {
                }

            })
        }
    }

    fun sendMessagetext(messagetext: String, mChatItem: Chat) {

        var message = Message(uid, messagetext, "", DateTimeUltil.getTimeCurrent())
        databaseReference.child(Contans.CHAT).child(uid).child(mChatItem.uIdFriend).push().setValue(message)
        var messageLast = Messagelast(DateTimeUltil.getTimeCurrent(), messagetext)
        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(mChatItem.uIdFriend).child(Contans.MESSAGE_LAST).setValue(messageLast)
        databaseReference.child(Contans.MESSAGE_LASTS).child(mChatItem.uIdFriend).child(uid).child(Contans.MESSAGE_LAST).setValue(messageLast)
    }
}