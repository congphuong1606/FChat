package vn.phuongcong.fchat.ui.main.fragment.listmsg

import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject


class MsgFragment : BaseFragment() ,ListMsgView{
    @Inject
    lateinit var mPresenter: ListMsgPresenter
    override val LayoutId: Int
        get() = R.layout.fragment_listmsg

    override fun injectDependence() {
        App().get(context)
                .plus(ViewModule(this))
                .injectTo(this)
    }

    override fun initData() {

    }

    override fun onDestroyComposi() {

    }


}
