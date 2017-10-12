package vn.phuongcong.fchat.di.login

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.LoginActivity
import javax.inject.Singleton

/**
 * Created by Ominext on 10/12/2017.
 */
@Module
class LoginModule(var loginActivity: LoginActivity) {
    @Provides
    @Singleton
    fun provideLoginActivity():LoginActivity= loginActivity

//    var loginActivity: LoginActivity?=null
//    constructor( loginActivity: LoginActivity):this(){
//        this.loginActivity=loginActivity
//    }
//    @Provides
//    @Singleton
//    fun provideLoginActivity():LoginActivity= loginActivity
}