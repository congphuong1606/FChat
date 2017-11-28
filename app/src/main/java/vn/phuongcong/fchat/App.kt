package vn.phuongcong.fchat

import android.app.Application
import android.content.Context
import vn.phuongcong.fchat.model.User


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.google.firebase.messaging.FirebaseMessaging
import vc908.stickerfactory.StickersManager
import vc908.stickerfactory.billing.Prices
import vc908.stickerfactory.utils.Utils
import vn.phuongcong.fchat.ui.chat.sticker.ShopActivity
import java.util.HashMap


class App : MultiDexApplication() {
    var UID: String? = null
    var UEMAIL: String? = null
    var UNAME: String? = null
    var UAVATAR: String? = null
    var position: Int = 0

    companion object Factory {
        var instance: App? = null

        fun getIns(): App? {
            if (instance == null) {
                instance = App()
            }
            return instance
        }


    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        MultiDex.install(this)
        FirebaseMessaging.getInstance().subscribeToTopic("Android")


        //sticker
        StickersManager.initialize("3bcd2ccb8f81e730fdd8c4b6d0f6da1a", this);
// set user neta info
        val meta = HashMap<String, String>()
        meta.put(vc908.stickerfactory.User.KEY_GENDER, vc908.stickerfactory.User.GENDER_FEMALE)
        meta.put(vc908.stickerfactory.User.KEY_AGE, "33")
        // Put your user id when you know it
        StickersManager.setUser(Utils.getDeviceId(this), meta)
        // Set sender id for push notifications
//        GcmManager.setGcmSenderId(this, "86472317986")
//        // Set push notification listener
//        GcmManager.setPushNotificationManager(PushNotificationManager())
        // Set custom shop class
        StickersManager.setShopClass(ShopActivity::class.java)
        // Set prices
        StickersManager.setPrices(Prices()
                .setPricePointB("$0.99", 9.99f)
                .setPricePointC("$1.99", 19.99f)
                // sku used for inapp purchases
                //                .setSkuB("pack_b")
                //                .setSkuC("pack_c")
        )
        // licence key for inapp purchases
        StickersManager.setLicenseKey("YOUR LICENCE KEY")
//        JpushManager.init(this)
    }

    fun get(context: Context): AppComponent {
        var app = context.applicationContext as App
        return app.component
    }

    fun setInforUser(user: User) {
        var UID = user.id
        var UEMAIL = user.email
        var UNAME = user.name
        var UAVATAR = user.avatar
    }

    fun getUID(context: Context): String? {
        var app = context.applicationContext as App
        return app.UID
    }

}