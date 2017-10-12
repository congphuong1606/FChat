package vn.phuongcong.fchat.di.component

import dagger.Component
import vn.phuongcong.fchat.MyApp
import vn.phuongcong.fchat.di.login.LoginModule
import vn.phuongcong.fchat.di.module.FirebaseModule

import vn.phuongcong.fchat.di.module.MyAppModule
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */
@Singleton
@Component(modules = arrayOf(MyAppModule::class,FirebaseModule::class))
interface MyAppComponent {
    fun inject(app: MyApp)
    fun plus(loginModule: LoginModule):SubComponent

}