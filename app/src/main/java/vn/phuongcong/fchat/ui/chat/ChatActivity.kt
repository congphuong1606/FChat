package vn.phuongcong.fchat.ui.chat

import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R

import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import javax.inject.Inject


class ChatActivity : BaseActivity(),ChatView {
    override val contentLayoutID: Int
        get() = R.layout.fragment_chat

    override fun onClick() {

    }

    override fun initData() {

    }

    @Inject
    lateinit var mPresenter: ChatPresenter


    override fun injectDependence() {

    }



}
