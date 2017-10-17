package vn.phuongcong.fchat.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.regis.RegisView
import javax.inject.Singleton

/**
 * Created by congp on 10/14/2017.
 */
@Module
class SharedPreference {

    @Provides
    fun provideSharedPre(context: Context):SharedPreferences {
       return context.getSharedPreferences(Contans.SPF_NAME,Context.MODE_PRIVATE)
   }
    @Provides
    fun provideSharedPreEditor(context: Context):SharedPreferences.Editor
            =context.getSharedPreferences(Contans.SPF_NAME,Context.MODE_PRIVATE)!!.edit()
}

