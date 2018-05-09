package vn.phuongcong.fchat.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import com.google.firebase.database.FirebaseDatabase
import vn.phuongcong.fchat.common.Contans

/**
 * Created by Ominext on 11/1/2017.
 */
class OnlineService : Service() {
    override fun onBind(p0: Intent?): IBinder {
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        val service: OnlineService
            get() = this@OnlineService
    }

    private lateinit var updateOnline: CountDownTimer

    override fun onCreate() {
        super.onCreate()
        updateOnline = object : CountDownTimer(System.currentTimeMillis(), Contans.TIME_TO_SFRESH) {
            override fun onTick(l: Long) {
                updateUserTimeStamp(applicationContext)
            }
            override fun onFinish() {
            }
        }
        updateOnline.start()
    }

    fun updateUserTimeStamp(context: Context) {
        if (isNetworkConnected(context)) {
            var sPref=context.getSharedPreferences(Contans.SPF_NAME,Context.MODE_PRIVATE)
            var dbReference=FirebaseDatabase.getInstance().reference
            val uId = sPref.getString(Contans.PRE_USER_ID,"")
            if (uId != "") {
                dbReference.child(Contans.USERS_PATH).child(uId).child(Contans.TIME_STAMP).setValue(System.currentTimeMillis())
            }
        }
    }

    fun isNetworkConnected(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        } catch (e: Exception) {
            return true
        }

    }
}