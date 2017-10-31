package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.content.SharedPreferences
import com.google.firebase.database.*
import com.pawegio.kandroid.i
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.User
import java.util.HashMap
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class FriendPresenter @Inject constructor(var sPref: SharedPreferences,
                                          var dbReference: DatabaseReference,
                                          var friendView: FriendView) {
    fun onLoadFriendIds() {
        var uid = sPref.getString(Contans.PRE_USER_ID, "")
        dbReference.child(Contans.FRIEND_PATH).child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                friendView.onRequestFailure(error.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    val mapRecord = dataSnapshot.value as HashMap<*, *>?
                    val listKey = mapRecord!!.keys.iterator()
                    while (listKey.hasNext()) {
                        val key = listKey.next().toString()
                        loadFriends(mapRecord[key].toString())
                    }
                }
            }
        })


    }


    private fun loadFriends(id: String) {
        dbReference.child(Contans.USERS_PATH).child(id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var user = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            friendView.onLoadFriendsSuccess(user)
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        friendView.onRequestFailure(error.toString())
                    }
                })
    }

    fun findIDEmail(email: String) {
        dbReference.child(Contans.USERS_PATH).orderByChild(Contans.EMAIL_PATH).equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    friendView.onRequestFailure("không tìm thấy email này")
                } else {
                    val id = (dataSnapshot.value as HashMap<*, *>).keys.iterator().next().toString()
                    if (id.equals(sPref.getString(Contans.PRE_USER_ID, ""))) {
                        friendView.onRequestFailure("email này là của bạn")
                    } else {
                        dbReference.child(Contans.FRIEND_PATH).child(sPref.getString(Contans.PRE_USER_ID, "")).child(id).setValue(id)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        loadFriends(id)
                                        friendView.onAddFriendSuccessful()
                                    }
                                }
                                .addOnFailureListener {
                                    friendView.onRequestFailure("thêm bạn không thành công")
                                }
                    }
                }
            }

        });

    }

    fun deleteFriend(id: String) {
        var uid = sPref.getString(Contans.PRE_USER_ID, "")
        dbReference.child(Contans.FRIEND_PATH).child(uid).child(id).removeValue()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful)
                        friendView.onDeleteFriendSuccess()
                }.addOnFailureListener { exception ->
            friendView.onRequestFailure("xóa bạn không thành công")
        }
    }
}