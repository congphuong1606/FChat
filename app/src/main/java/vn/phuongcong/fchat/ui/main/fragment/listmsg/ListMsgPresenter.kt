package vn.phuongcong.fchat.ui.main.fragment.listmsg

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.common.Contans
import javax.inject.Inject
import android.content.SharedPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.model.User

/**
 * Created by Ominext on 10/18/2017.
 */
class ListMsgPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                           var databaseReference: DatabaseReference,
                                           var listMsgView: ListMsgView,
                                           var sPref: SharedPreferences) {
    // = sPref.getString(Contans.PRE_USER_ID, "")
    var uid= mAuth.currentUser!!.uid
    var listUserChat = mutableListOf<User>()

    fun loadListChat() {
        databaseReference.child(Contans.CHAT).child(uid).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                listMsgView.onRequestFailure(databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var listUid = mutableListOf<String>()
                for (user in dataSnapshot!!.children) {
                    listUid.add(user.key.toString())
                }
                getProfileByUid(listUid)
                getListMessageLastByUid(listUid)
            }
        })
    }

    private fun getListMessageLastByUid(listUid: MutableList<String>) {
        var listMessagelast :MutableList<Messagelast> = mutableListOf()

        for(idFriend in listUid){
            databaseReference.child(Contans.MESSAGE_LASTS).child(uid).child(idFriend).child(Contans.MESSAGE_LAST).addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot?) {

                    listMessagelast.add(dataSnapshot!!.getValue(Messagelast::class.java)!!)
                    listMsgView.onLoadListMessagelast(listMessagelast)
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
                            if(listUserChat.size>0){
                                if(listUserChat.contains(user.getValue(User::class.java)!!)){
                                    listUserChat.add(user.getValue(User::class.java)!!)
                                }

                            }else{
                                listUserChat.add(user.getValue(User::class.java)!!)
                            }

                        }
                    }
                }
                listMsgView.OnLoadListChatSuccess(listUserChat)
            }
        })

    }




}