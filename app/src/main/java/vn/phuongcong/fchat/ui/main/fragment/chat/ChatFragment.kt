package vn.phuongcong.fchat.ui.main.fragment.chat

import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject


class ChatFragment : BaseFragment(),ChatView {
    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    @Inject
    lateinit var mPresenter: ChatPresenter
    override val LayoutId: Int
        get() = R.layout.fragment_chat

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData(v: View) {

    }

    override fun onDestroyComposi() {

    }

}
