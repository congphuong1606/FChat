package vn.phuongcong.fchat.di.component

import dagger.Subcomponent
import vn.phuongcong.fchat.LoginActivity
import vn.phuongcong.fchat.di.login.LoginModule

/**
 * Created by Ominext on 10/12/2017.
 */
@Subcomponent(modules = arrayOf(LoginModule::class))
interface SubComponent {
    fun injectTo(loginActivity: LoginActivity)
}