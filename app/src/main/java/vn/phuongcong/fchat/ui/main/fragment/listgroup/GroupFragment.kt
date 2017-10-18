package vn.phuongcong.fchat.ui.main.fragment.listgroup

import android.os.Bundle
import android.view.View
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.base.BaseFragment
import javax.inject.Inject


class GroupFragment : BaseFragment() ,GroupView{
    @Inject
    lateinit var presenter: GroupPresenter



    override val LayoutId: Int
        get() = R.layout.fragment_group

    override fun injectDependence() {
       App.get().component.plus(ViewModule(this)).injectTo(this)

    }

    override fun initData() {

    }

    override fun onDestroyComposi() {
    }


}
