package vn.phuongcong.fchat.common.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Created by Ominext on 11/10/2017.
 */
class PermissionUtil {
    companion object {
        fun requestPermission(activity: Activity, permission: String) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), 0)
            }
        }
    }
}