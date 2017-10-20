package vn.phuongcong.fchat

import android.app.Application
import android.content.Context
import vn.phuongcong.fchat.data.User


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule


class App : Application() {
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