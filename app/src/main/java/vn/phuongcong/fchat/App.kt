package vn.phuongcong.fchat

import android.app.Application
import com.google.firebase.auth.FirebaseAuth


import vn.phuongcong.fchat.di.component.AppComponent
import vn.phuongcong.fchat.di.component.DaggerAppComponent
import vn.phuongcong.fchat.di.module.AppModule


class App : Application() {

    companion object Factory {
        fun get(): App = App()

    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }


    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}