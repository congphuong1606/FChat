package vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import vn.phuongcong.fchat.common.utils.DatabaseRef
import javax.inject.Inject

/**
 * Created by vietcoscc on 14/11/2017.
 */
class FriendAddingPresenter @Inject constructor(var friendAddingView: FriendAddingView) {
    fun receiveFriendData(uid: String) {
        DatabaseRef.friendInfoRef(uid).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                var friendId = p0!!.getValue(String::class.java)
                if (friendId != null) {
                    friendAddingView.showFriend(friendId)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}