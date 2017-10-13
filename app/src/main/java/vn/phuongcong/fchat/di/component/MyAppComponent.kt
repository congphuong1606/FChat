package vn.phuongcong.fchat.di.component

import dagger.Component
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.di.module.FirebaseModule

import vn.phuongcong.fchat.di.module.MyAppModule
import vn.phuongcong.fchat.di.module.ViewModule
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */
@Singleton
@Component(modules = arrayOf(MyAppModule::class,FirebaseModule::class))
interface MyAppComponent {
    fun inject(app: App)
    fun plus(viewModule: ViewModule):SubComponent

}