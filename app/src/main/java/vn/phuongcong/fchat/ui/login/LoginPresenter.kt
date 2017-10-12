package vn.phuongcong.fchat.ui.login

import vn.phuongcong.fchat.base.BasePresenter

/**
 * Created by Ominext on 10/11/2017.
 */
interface LoginPresenter : BasePresenter {
    fun attackLoginView(loginView: LoginView)
    fun onSignIn(email: String, pass: String)

}