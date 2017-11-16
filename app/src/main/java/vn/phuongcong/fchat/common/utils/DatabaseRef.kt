package vn.phuongcong.fchat.common.utils

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

/**
 * Created by Administrator on 10/24/2017.
 */
class DatabaseRef {
    companion object {
        // Group
        val GROUP = "GROUP"
        val GROUP_BASIC_INFO = "GROUP_BASIC_INFO"
        val GROUP_MEMBER = "GROUP_MEMBER"
        val GROUP_CHAT_CONTENT = "GROUP_CHAT_CONTENT"
        val USER = "USER"
        val OWN = "OWN"
        val OTHERS = "OTHERS"
        val ADMIN_KEY = "ADMIN_KEY"
        val GROUP_KEY = "GROUP_KEY"
        var databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference
        // Friend
        val FRIEND = "FRIEND"

        fun groupRef(): DatabaseReference = databaseRef.child(GROUP)

        fun groupInfoRef(uid: String): DatabaseReference =
                groupRef().child(GROUP_BASIC_INFO).child(uid)

        fun ownGroupInfoRef(uid: String): DatabaseReference =
                groupRef().child(GROUP_BASIC_INFO).child(uid).child(OWN)

        fun othersGroupInfoRef(uid: String): DatabaseReference =
                groupRef().child(GROUP_BASIC_INFO).child(uid).child(OTHERS)

        fun groupContentRef(uid: String): DatabaseReference =
                groupRef().child(GROUP_CHAT_CONTENT).child(uid)

        fun groupMemberRef(uid: String): DatabaseReference =
                groupRef().child(GROUP_MEMBER).child(uid)

        fun userRef(): DatabaseReference = databaseRef.child(USER)

        fun userInfoRef(uid: String): DatabaseReference = userRef().child(uid)

        fun friendRef(): DatabaseReference = databaseRef.child(FRIEND)

        fun friendInfoRef(uid: String): DatabaseReference = friendRef().child(uid)
    }
}