package vn.phuongcong.fchat.fcm

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.R.attr.author
import android.media.RingtoneManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.main.MainActivity



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
        FirebaseMessaging.getInstance().subscribeToTopic("Android")
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage!!.getData().size > 0) {
            showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"))
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {

        }

    }

    private fun showNotification(title: String?, content: String?) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle("New Article: " + title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("By " + content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


}