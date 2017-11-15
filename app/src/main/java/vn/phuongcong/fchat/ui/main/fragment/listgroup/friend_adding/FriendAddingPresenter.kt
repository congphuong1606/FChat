package vn.phuongcong.fchat.ui.main.fragment.listgroup.friend_adding

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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

    fun receiveCurrentMemberData(adminKey: String, groupKey: String) {
        DatabaseRef.groupMemberRef(adminKey).child(groupKey).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    var arrMember: MutableList<String> = arrayListOf()
                    var arrDatasnapshot: Iterable<DataSnapshot> = p0.children
                    for (member in arrDatasnapshot!!) {
                        var uid = member.getValue(String::class.java)
                        arrMember.add(uid!!)

                    }
                    friendAddingView.showMember(arrMember)
                }
            }
        })
    }
}