package vn.phuongcong.fchat.ui.main.fragment.listmsg

import com.google.firebase.auth.FirebaseAuth
import vn.phuongcong.fchat.common.Contans
import javax.inject.Inject
import android.content.SharedPreferences
import com.google.firebase.database.*
import vn.phuongcong.fchat.model.Chat
import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.model.User

/**
 * Created by Ominext on 10/18/2017.
 */
class ListMsgPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                           var databaseReference: DatabaseReference,
                                           var listMsgView: ListMsgView,
                                           var sPref: SharedPreferences) {
    //    var uid= sPref.getString(Contans.PRE_USER_ID, "")
    var uid = mAuth.currentUser!!.uid
    var listUserChat = mutableListOf<User>()

    fun loadListChat() {
        databaseReference.child(Contans.CHAT).child(uid).addValueEventListener(object : ValueEventListener {

            override fun onCancelled(databaseError: DatabaseError?) {
                listMsgView.onRequestFailure(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val listUid = mutableListOf<String>()
                dataSnapshot!!.children.mapTo(listUid) { it.key.toString() }
                if (listUid.size == dataSnapshot.children.count())
                    getProfileByUid(listUid)

                if (databaseReference.child(Contans.MESSAGE_LAST).child(uid) != null) {
                    getListMessageLastByUid(listUid)
                }

            }
        })


    }

    private fun getListMessageLastByUid(listUid: MutableList<String>) {
        var listMessagelast: MutableList<Messagelast> = mutableListOf()


        for (idFriend in listUid) {
            databaseReference.child(Contans.MESSAGE_LASTS)!!.child(uid).child(idFriend).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    for (chil in dataSnapshot!!.children) {
                        val messageLast: Messagelast = chil.getValue(Messagelast::class.java)!!
                        listMessagelast.add(messageLast)
                    }

                    if (listMessagelast.size == listUid.size) {
                        listMsgView.onLoadListMessagelast(listMessagelast)
                    }

                }
            })

        }
    }


    private fun getProfileByUid(listUid: MutableList<String>) {
        databaseReference.child(Contans.USERS_PATH).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(data: DataSnapshot?) {
                for (uid in listUid) {
                    for (user in data!!.children) {
                        if (user.key == uid) {
                            if (listUserChat.size > 0) {
                                if (!listUserChat.contains(user.getValue(User::class.java)!!)) {
                                    listUserChat.add(user.getValue(User::class.java)!!)
                                }
                            } else {
                                listUserChat.add(user.getValue(User::class.java)!!)
                            }
                        }
                    }
                }
                if (listUserChat.size == listUid.size) {
                    listMsgView.OnLoadListChatSuccess(listUserChat)
                    listUserChat= mutableListOf()
                }
            }
        })
    }

    fun deleteChat(chat: Chat) {
        databaseReference.child(Contans.CHAT).child(uid).child(chat.uIdFriend).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(chat.uIdFriend).removeValue()
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful)
                                        listMsgView.onDeleteChatSuccess()
                                }.addOnFailureListener { exception ->
                            listMsgView.onDeleteChatFail(exception.message)
                        }
                    }

                }.addOnFailureListener { exception ->
            listMsgView.onDeleteChatFail(exception.message)
        }
    }
}
