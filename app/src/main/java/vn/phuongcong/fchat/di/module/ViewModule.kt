package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.main.MainView
import vn.phuongcong.fchat.ui.main.fragment.addfriend.AddFriendView
import vn.phuongcong.fchat.ui.regis.RegisView
import javax.inject.Singleton

/**
 * Created by Ominext on 10/13/2017.
 */
@Module
class ViewModule {
    lateinit var loginView: LoginView
    lateinit var regisView: RegisView
    lateinit var mainView: MainView
    lateinit var addFriendView: AddFriendView


    @Provides
    @ActivityScope
    fun provideLoginView(): LoginView = loginView

    @Provides
    @ActivityScope
    fun provideRegisView(): RegisView = regisView

    @Provides
    @ActivityScope
    fun provideMainVIew(): MainView = mainView

    @Provides
    @ActivityScope
    fun provideAddFriendView(): AddFriendView = addFriendView

    constructor(addFriendView: AddFriendView){
        this.addFriendView=addFriendView
    }
    constructor(mainView: MainView) {
        this.mainView = mainView
    }

    constructor(loginView: LoginView) {
        this.loginView = loginView
    }

    constructor(regisView: RegisView) {
        this.regisView = regisView
    }

}