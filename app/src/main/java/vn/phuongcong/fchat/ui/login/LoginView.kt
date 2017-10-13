package vn.phuongcong.fchat.ui.login

import vn.phuongcong.fchat.base.BaseView

/**
 * Created by Ominext on 10/11/2017.
 */
interface LoginView:BaseView{
    fun onLoginSuccessfull();
    fun onVerified()
    fun onViriFail()
}