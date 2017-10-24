package vn.phuongcong.fchat.utils

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

/**
 * Created by Administrator on 10/24/2017.
 */
class DatabaseRef {
    companion object {
        val GROUP = "GROUP"
        val GROUP_BASIC_INFO = "GROUP_BASIC_INFO"
        val GROUP_MEMBER = "GROUP_MEMBER"
        val GROUP_CHAT_CONTENT = "GROUP_CHAT_CONTENT"

        var databaseRef: DatabaseReference = FirebaseDatabase.getInstance().reference;

        fun groupRef(): DatabaseReference {
            return databaseRef.child(GROUP)
        }

        fun groupInfoRef(): DatabaseReference {
            return groupRef().child(GROUP_BASIC_INFO)
        }

        fun groupContentRef(): DatabaseReference {
            return groupRef().child(GROUP_CHAT_CONTENT)
        }

        fun groupMemberRef(): DatabaseReference {
            return groupRef().child(GROUP_MEMBER);
        }
    }
}