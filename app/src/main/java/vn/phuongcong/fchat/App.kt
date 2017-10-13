package vn.phuongcong.fchat

import android.app.Application
import vn.phuongcong.fchat.di.component.DaggerMyAppComponent

import vn.phuongcong.fchat.di.component.MyAppComponent
import vn.phuongcong.fchat.di.module.MyAppModule


class App : Application() {

    val component: MyAppComponent by lazy {
        DaggerMyAppComponent.builder().myAppModule(MyAppModule(this)).build()
    }



    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}