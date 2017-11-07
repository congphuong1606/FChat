package vn.phuongcong.fchat.ui.main.fragment.listgroup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import vn.phuongcong.fchat.data.model.Group
import vn.phuongcong.fchat.common.utils.DatabaseRef
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class GroupPresenter @Inject constructor(
        var databaseReference: DatabaseReference,
        var groupView: GroupView) {
    var mAuth = FirebaseAuth.getInstance()

    fun receiveGroupData() {
        DatabaseRef.ownGroupInfoRef(mAuth.currentUser!!.uid).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, key: String?) {
                var group: Group = dataSnapshot.getValue(Group::class.java)!!
                group.groupKey = dataSnapshot.key
                groupView.showGroup(group)
            }

            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }
        })
    }

    fun createGroup(group: Group) {
        DatabaseRef.ownGroupInfoRef(mAuth.currentUser!!.uid).push().setValue(group).addOnSuccessListener {
            groupView.showToast("Susscessful ")
        }.addOnFailureListener {
            groupView.showToast("Failed ")
        }
    }
}