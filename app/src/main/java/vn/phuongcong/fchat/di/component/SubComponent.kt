package vn.phuongcong.fchat.di.component

import dagger.Subcomponent
import vn.phuongcong.fchat.LoginActivity
import vn.phuongcong.fchat.RegisActivity
import vn.phuongcong.fchat.di.module.SharedPreference
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.di.scope.ActivityScope
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupFragment

/**
 * Created by Ominext on 10/12/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ViewModule::class,SharedPreference::class))
interface SubComponent {
    fun injectTo(loginActivity: LoginActivity)
    fun injectTo(regisActivity: RegisActivity)
    fun injectTo(groupFragment: GroupFragment)
}