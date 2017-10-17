package vn.phuongcong.fchat.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.App
import java.security.AccessController.getContext
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */


@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp():App = app

    @Provides
    @Singleton
    fun provideContext() :Context= app.applicationContext


//    @Provides
//    @Singleton
//    fun  provideContext(): Context =context

}