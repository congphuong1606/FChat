package vn.phuongcong.fchat.di.component

import dagger.Component
import vn.phuongcong.fchat.LoginActivity
import vn.phuongcong.fchat.MyApp
import vn.phuongcong.fchat.di.module.AppModule
import vn.phuongcong.fchat.di.module.FireBaseModule
import javax.inject.Singleton

/**
 * Created by Ominext on 10/11/2017.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class,FireBaseModule::class))
interface AppComponent {
    fun injectTo(a:LoginActivity)
    fun inject(app: MyApp)
}