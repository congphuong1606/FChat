package vn.phuongcong.fchat.fcm

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Ominext on 11/16/2017.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {


    companion object {
        var REPLY_ACTION = "vn.phuongcong.fchat.fcm.REPLY_ACTION"
        private val KEY_REPLY = "key_reply_message"


    }
    override fun onCreate() {
        super.onCreate()
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.d(TAG, "From: " + remoteMessage!!.getFrom())
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody())

    }


}