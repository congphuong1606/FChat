package vn.phuongcong.fchat.common.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Ominext on 11/1/2017.
 */

object ServiceUtils {
    fun isNetworkConnected(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        } catch (e: Exception) {
            return true
        }

    }
}
