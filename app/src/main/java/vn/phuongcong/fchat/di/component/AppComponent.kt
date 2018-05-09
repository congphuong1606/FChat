package vn.phuongcong.fchat.di.component

import dagger.Component
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.di.module.FirebaseModule

import vn.phuongcong.fchat.di.module.AppModule
import vn.phuongcong.fchat.di.module.ViewModule
import javax.inject.Singleton

/**
 * Created by Phuongnv on 10/12/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class,FirebaseModule::class))
interface AppComponent {
    fun inject(app: App)
    fun plus(viewModule: ViewModule):SubComponent

}