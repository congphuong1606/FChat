package vn.phuongcong.fchat.di.module

import dagger.Module
import dagger.Provides
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.login.LoginView
import vn.phuongcong.fchat.ui.main.fragment.addFriend.AddFriendView
import vn.phuongcong.fchat.ui.main.fragment.chat.ChatView
import vn.phuongcong.fchat.ui.main.fragment.listfriend.FriendView
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupView
import vn.phuongcong.fchat.ui.main.fragment.listgroup.chat.ChatGroupView
import vn.phuongcong.fchat.ui.main.fragment.listmsg.ListMsgView
import vn.phuongcong.fchat.ui.profile.ProfileView
import vn.phuongcong.fchat.ui.regis.RegisView
import vn.phuongcong.fchat.ui.splash.SplashActivity
import vn.phuongcong.fchat.ui.splash.SplashView

/**
 * Created by Ominext on 10/13/2017.
 */
@Module
class ViewModule {
    lateinit var loginView: LoginView
    lateinit var regisView: RegisView
    lateinit var groupView: GroupView
    lateinit var chatView: ChatView
    lateinit var addFriendView: AddFriendView
    lateinit var friendView: FriendView
    lateinit var listMsgView: ListMsgView
    lateinit var profileView: ProfileView
    lateinit var splashView: SplashView
    lateinit var chatGroupView: ChatGroupView

    @Provides
    @ActivityScope
    fun provideLoginView(): LoginView = loginView

    @Provides
    @ActivityScope
    fun provideRegisView(): RegisView = regisView

    @Provides
    @ActivityScope
    fun provideGroupView(): GroupView = groupView

    @Provides
    @ActivityScope
    fun provideChatView(): ChatView = chatView

    @Provides
    @ActivityScope
    fun provideAddFriendView(): AddFriendView = addFriendView

    @Provides
    @ActivityScope
    fun provideFriendView(): FriendView = friendView

    @Provides
    @ActivityScope
    fun provideListMsgView(): ListMsgView = listMsgView

    @Provides
    @ActivityScope
    fun provideProfileView(): ProfileView = profileView

    @Provides
    @ActivityScope
    fun provideSplashView(): SplashView = splashView

    @Provides
    @ActivityScope
    fun provideChatGroupView(): ChatGroupView = chatGroupView

    constructor(loginView: LoginView) {
        this.loginView = loginView
    }

    constructor(regisView: RegisView) {
        this.regisView = regisView
    }

    constructor(groupView: GroupView) {
        this.groupView = groupView
    }

    constructor(chatView: ChatView) {
        this.chatView = chatView
    }

    constructor(friendView: FriendView) {
        this.friendView = friendView
    }

    constructor(addFriendView: AddFriendView) {
        this.addFriendView = addFriendView
    }

    constructor(listMsgView: ListMsgView) {
        this.listMsgView = listMsgView
    }

    constructor(profileView: ProfileView) {
        this.profileView = profileView
    }

    constructor(splashView: SplashView) {
        this.splashView = splashView
    }
    constructor(chatGroupView: ChatGroupView) {
        this.chatGroupView = chatGroupView
    }



}