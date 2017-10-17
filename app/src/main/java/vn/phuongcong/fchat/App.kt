package vn.phuongcong.fchat

import android.app.Application


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule


class App : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }



    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}