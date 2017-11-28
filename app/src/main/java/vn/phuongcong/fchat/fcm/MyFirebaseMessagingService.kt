package vn.phuongcong.fchat.fcm

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.event.IStatusListener
import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.main.fragment.listmsg.MsgFragment


/**
 * Created by Ominext on 11/16/2017.
 */
class MyFirebaseMessagingService : FirebaseMessagingService(), IStatusListener {
    private var keyTest: String = ""
    var fr: MsgFragment = MsgFragment(this)
    override fun sendStatus(keyID: String) {
        keyTest = keyID
    }


    companion object {
        var REPLY_ACTION = "vn.phuongcong.fchat.fcm.REPLY_ACTION"
        private val KEY_REPLY = "key_reply_message"


    }


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.e("onMessageReceived", "" + App.getIns()!!.position)
        if (App.getIns()!!.position == 0) {
            if (remoteMessage!!.getData().size > 0) {
                showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"))

            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {

            }
        }


    }

    private fun showNotification(title: String?, content: String?) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(content)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setColor(resources.getColor(R.color.blue))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


}