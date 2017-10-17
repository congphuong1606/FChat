package vn.phuongcong.fchat

import android.app.Application


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule


class App : Application() {

    var UID=""
    lateinit var myApplication:App
    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    companion object Factory {
        fun create(): App = App()
    }

    override fun onCreate() {
        super.onCreate()
        myApplication=this
        component.inject(this)
    }

    fun get(): App {
            return myApplication
    }



}