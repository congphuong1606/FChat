package vn.phuongcong.fchat.ui.chat

import android.os.Bundle
import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.data.User

import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ChatActivity : BaseActivity(), ChatView {
    var friendChat: User? = null

    @Inject
    lateinit var mPresenter: ChatPresenter
    override val contentLayoutID: Int
        get() = R.layout.fragment_chat

    override fun onClick() {

    }

    override fun initData() {
        getFriendChat()
        updateView()
    }

    private fun updateView() {
        mPresenter.onLoadListMsg(friendChat!!)
    }

    private fun getFriendChat() {
        var b: Bundle = intent.getBundleExtra("b")
        friendChat  = b.getSerializable("friend") as User
    }

    override fun injectDependence() {
        App().get(this).plus(ViewModule(this))
                .injectTo(this)
    }

    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }


}
