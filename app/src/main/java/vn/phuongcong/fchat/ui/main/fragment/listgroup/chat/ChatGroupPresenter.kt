package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.utils.CalendarUtils
import vn.phuongcong.fchat.utils.DatabaseRef
import javax.inject.Inject

/**
 * Created by vietcoscc on 01/11/2017.
 */
class ChatGroupPresenter @Inject constructor(var chatGroupView: ChatGroupView) {
    var mAuth = FirebaseAuth.getInstance().currentUser
    fun onChat(content: String, msgImage: String, adminKey: String, groupKey: String) {
        var currentTime = CalendarUtils.currentTime() + " " + CalendarUtils.currentDate()
        var message = Message(mAuth!!.uid, content, "", currentTime)
        DatabaseRef.groupContentRef(adminKey).child(groupKey).push().setValue(message)
    }

    fun receiveChatData(adminKey: String, groupKey: String) {
        DatabaseRef.groupContentRef(adminKey).child(groupKey).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                var message: Message = p0!!.getValue(Message::class.java)!!
                chatGroupView.showChatItem(message)
            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

        })
    }
}