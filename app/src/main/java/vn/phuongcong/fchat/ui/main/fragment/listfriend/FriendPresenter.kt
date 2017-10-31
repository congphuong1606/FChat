package vn.phuongcong.fchat.ui.main.fragment.listfriend

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.model.Friend
import vn.phuongcong.fchat.model.User
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class FriendPresenter @Inject constructor(var mAuth: FirebaseAuth,
                                          var sPref: SharedPreferences,
                                          var dbReference: DatabaseReference,
                                          var friendView: FriendView) {
    fun onLoadFriendIds() {

        var uid = sPref.getString(Contans.PRE_USER_ID, "")
        dbReference.child(Contans.FRIEND_PATH).child(uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (data in dataSnapshot.children) {
                            val id=data.getValue(Friend::class.java)!!.id
                            loadFriends(id)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        friendView.onRequestFailure(error.toString())
                    }
                })
    }
    private fun loadFriends(id: String) {
        dbReference.child(Contans.USERS_PATH).child(id)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        friendView.onLoadFriendsSuccess(user!!)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        friendView.onRequestFailure(error.toString())
                    }
                })
    }

}