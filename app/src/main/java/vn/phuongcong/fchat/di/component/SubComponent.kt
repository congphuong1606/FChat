package vn.phuongcong.fchat.di.component

import dagger.Subcomponent
import vn.phuongcong.fchat.LoginActivity
import vn.phuongcong.fchat.RegisActivity
import vn.phuongcong.fchat.di.module.SharedPreference
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.chat.ChatActivity
import vn.phuongcong.fchat.ui.main.MainActivity
import vn.phuongcong.fchat.ui.main.fragment.listfriend.FriendFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.chat.ChatGroupActivity
import vn.phuongcong.fchat.ui.main.fragment.listmsg.MsgFragment
import vn.phuongcong.fchat.ui.profile.ProfileActivity
import vn.phuongcong.fchat.ui.splash.SplashActivity

/**
 * Created by Ominext on 10/12/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ViewModule::class, SharedPreference::class))
interface SubComponent {
    fun injectTo(loginActivity: LoginActivity)
    fun injectTo(regisActivity: RegisActivity)
    fun injectTo(groupFragment: GroupFragment)
    fun injectTo(friendFragment: FriendFragment)
    fun injectTo(msgFragment: MsgFragment)
    fun injectTo(profileActivity: ProfileActivity)
    fun injectTo(splashActivity: SplashActivity)
    fun injectTo(chatGroupActivity: ChatGroupActivity)
    fun injectTo(mainActivity: MainActivity)
    fun injectTo(chatActivity: ChatActivity)

}