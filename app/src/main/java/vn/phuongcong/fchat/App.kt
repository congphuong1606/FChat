package vn.phuongcong.fchat

import android.app.Application
import android.content.Context


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule


class App : Application() {
    var UID = ""
    var UEMAIL = ""
    var UNAME = ""
    var UAVATAR = ""

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
    fun get(context:Context):AppComponent{
        var app= context.applicationContext as App
        return app.component
    }
}