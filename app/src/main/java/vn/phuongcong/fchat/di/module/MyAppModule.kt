package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.App
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */


@Module
class MyAppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp() = app
}