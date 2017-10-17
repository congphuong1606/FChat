package vn.phuongcong.fchat.ui.login

import vn.phuongcong.fchat.data.User
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/11/2017.
 */
interface LoginView: BaseView {
    fun onLoginSuccessfull();
    fun onViriFail()
    fun  onVerified(user: User?)
}