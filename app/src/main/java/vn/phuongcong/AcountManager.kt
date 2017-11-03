package vn.phuongcong

import android.accounts.AccountManager
import vn.phuongcong.fchat.App

/**
 * Created by Ominext on 10/17/2017.
 */
class AcountManager() {
    companion object Factory {
        fun create(): AcountManager = AcountManager()
    }

    lateinit var acount: AcountManager
    fun getInstance(): AcountManager {
        if (acount == null) {
//            acount = AccountManager();
        }
        return acount;
    }


}