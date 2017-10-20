package vn.phuongcong.fchat.ui.main.fragment.addFriend


import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject


class AddFriendFragment : BaseFragment(),AddFriendView {


    @Inject
    lateinit var mPresenter: AddFriendPresenter

    override val LayoutId: Int
        get() = R.layout.fragment_add_friend

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
