package vn.phuongcong.fchat.ui.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import vn.phuongcong.fchat.R

abstract class BaseFragment : Fragment() {
    protected abstract val LayoutId: Int
    protected abstract fun injectDependence()
    protected abstract fun initData()

    protected abstract fun onDestroyComposi()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fun View(): View = inflater!!.inflate(LayoutId, container, false)
        injectDependence()
        initData()
        return View();
    }


    override fun onDetach() {
        super.onDetach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyComposi()
    }

}
