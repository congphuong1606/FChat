package vn.phuongcong.fchat.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.MyApp
import javax.inject.Singleton

/**
 * Created by Ominext on 10/11/2017.
 */
@Module
class AppModule(var myApp: MyApp) {
    @Provides
    @Singleton
    fun getApplicationContext(): Context = myApp
}