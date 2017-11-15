package vn.phuongcong.fchat.ui.main.fragment.listgroup

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import vn.phuongcong.fchat.model.Group
import vn.phuongcong.fchat.common.utils.DatabaseRef
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.groupInfoRef
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.groupMemberRef
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.othersGroupInfoRef
import vn.phuongcong.fchat.common.utils.DatabaseRef.Companion.ownGroupInfoRef
import javax.inject.Inject

/**
 * Created by Ominext on 10/18/2017.
 */
class GroupPresenter @Inject constructor(
        var databaseReference: DatabaseReference,
        var groupView: GroupView) {
    var mAuth = FirebaseAuth.getInstance()

    fun receiveGroupData() {
        ownGroupInfoRef(mAuth.currentUser!!.uid).addChildEventListener(object : ChildEventListener {
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

    fun receiveOtherGroupData() {
        othersGroupInfoRef(mAuth.currentUser!!.uid).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                if (p0!!.exists()) {
                    var groupKey = p0!!.getValue(String::class.java)
                    groupInfoRef(p0.key).child(groupKey)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(p0: DatabaseError?) {

                                }

                                override fun onDataChange(p0: DataSnapshot?) {
                                    var group: Group = p0!!.getValue(Group::class.java)!!
                                    group.groupKey = p0!!.key
                                    groupView.showGroup(group)
                                }
                            })

                }

            }

            override fun onChildRemoved(p0: DataSnapshot?) {

            }

        })
    }

    fun createGroup(group: Group) {
        var groupKey = ownGroupInfoRef(mAuth.currentUser!!.uid).push().key
        ownGroupInfoRef(mAuth.currentUser!!.uid)
                .child(groupKey).setValue(group)
                .addOnSuccessListener {
                    groupMemberRef(mAuth.currentUser!!.uid)
                            .child(groupKey).child(mAuth.currentUser!!.uid)
                            .setValue(mAuth.currentUser!!.uid)
                    groupView.showToast("Susscessful ")
                }
                .addOnFailureListener {
                    groupView.showToast("Failed ")
                }
    }
}