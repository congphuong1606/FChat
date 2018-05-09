package vn.phuongcong.fchat.ui.profile

import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ProfileView:BaseView {
    fun onSignOutSuccessful()
    fun onUpdateAvatarSuccessful(avatarUrl: String)
    fun onUpdateNameSuccessful(name: String)
    fun onUpdatePassWordSuccessfull()
}