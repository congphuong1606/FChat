package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.MyApp
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */


@Module
class MyAppModule(val app: MyApp) {
    @Provides
    @Singleton
    fun provideApp() = app
}