package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.regis.RegisView
import javax.inject.Singleton

/**
 * Created by Ominext on 10/13/2017.
 */
@Module
class ViewModule{
    lateinit var loginView: LoginView
    lateinit var regisView: RegisView




    @Provides
    @ActivityScope
    fun provideLoginView(): LoginView = loginView

    @Provides
    @ActivityScope
    fun provideRegisView(): RegisView = regisView

    constructor(loginView: LoginView)  {
        this.loginView = loginView
    }
    constructor(regisView: RegisView)  {
        this.regisView = regisView
    }

}