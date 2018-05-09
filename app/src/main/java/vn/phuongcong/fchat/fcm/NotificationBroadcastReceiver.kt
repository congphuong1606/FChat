package vn.phuongcong.fchat.fcm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Ominext on 11/16/2017.
 */
 class NotificationBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private val KEY_CONVERSATION_ID = "key_conversation_id"
        private val KEY_IS_GROUP = "key_conversation_type"
        private val KEY_NOTIFICATION_ID = "key_noticiation_id"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {

    }

    fun getReplyMessageIntent(context: Context, notificationId: Int, conversationId: String, isGroup: Boolean): Intent {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        intent.action = MyFirebaseMessagingService.REPLY_ACTION
        intent.putExtra(KEY_NOTIFICATION_ID, notificationId)
        intent.putExtra(KEY_CONVERSATION_ID, conversationId)
        intent.putExtra(KEY_IS_GROUP, isGroup)
        return intent
    }
}