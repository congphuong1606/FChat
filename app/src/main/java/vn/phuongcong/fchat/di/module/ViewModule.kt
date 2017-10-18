package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupView
import vn.phuongcong.fchat.ui.regis.RegisView
import javax.inject.Singleton

/**
 * Created by Ominext on 10/13/2017.
 */
@Module
class ViewModule{
    lateinit var loginView: LoginView
    lateinit var regisView: RegisView
    lateinit var groupView: GroupView




    @Provides
    @ActivityScope
    fun provideLoginView(): LoginView = loginView

    @Provides
    @ActivityScope
    fun provideRegisView(): RegisView = regisView
    @Provides
    @ActivityScope
    fun provideGROUP(): GroupView = groupView

    constructor(loginView: LoginView)  {
        this.loginView = loginView
    }
    constructor(regisView: RegisView)  {
        this.regisView = regisView
    }

    constructor(groupView: GroupView){
        this.groupView=groupView
    }

}