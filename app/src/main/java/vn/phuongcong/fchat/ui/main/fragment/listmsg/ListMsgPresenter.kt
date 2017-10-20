package vn.phuongcong.fchat.ui.main.fragment.listmsg

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.common.Contans
import javax.inject.Inject
import android.content.SharedPreferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.model.User

/**
 * Created by Ominext on 10/18/2017.
 */
class ListMsgPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                           var databaseReference: DatabaseReference,
                                           var listMsgView: ListMsgView,
                                           var sPref:SharedPreferences){
    var uid = sPref.getString(Contans.PRE_USER_ID, "")
    var nameUser=sPref.getString(Contans.PRE_USER_NAME,"")
    fun loadListChat() {
        databaseReference.child(Contans.CHAT).child(uid).addValueEventListener(object :ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                listMsgView.onRequestFailure(databaseError.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var listUid = mutableListOf<String>()
                for(user in dataSnapshot!!.children){
                    listUid.add(user.key.toString())
                }
                getProfileByUid(listUid)
            }
        })
    }

    private fun getProfileByUid(listUid: MutableList<String>) {
        var listUserChat = mutableListOf<User>()
                databaseReference.child(Contans.USERS_PATH).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(data: DataSnapshot?) {
                for(uid in listUid){
                    for(user in data!!.children){
                        if(user.key==uid){
                            listUserChat.add(user.getValue(User::class.java)!!)
                        }
                    }
                }
                listMsgView.OnLoadListChatSuccess(listUserChat)
            }
        })
    }

}