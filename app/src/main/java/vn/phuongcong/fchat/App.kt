package vn.phuongcong.fchat

import android.app.Application
import android.content.Context
import android.provider.Contacts.SettingsColumns.KEY
import vn.phuongcong.fchat.model.User


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import vc908.stickerfactory.StickersManager


class App : MultiDexApplication () {
    var UID: String? = null
    var UEMAIL: String? = null
    var UNAME: String? = null
    var UAVATAR: String? = null

    companion object Factory {
        fun create(): App = App()

    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        MultiDex.install(this)






        //sticker
        StickersManager.initialize("3bcd2ccb8f81e730fdd8c4b6d0f6da1a", this);

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