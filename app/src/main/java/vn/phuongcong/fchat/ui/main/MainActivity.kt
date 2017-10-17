package vn.phuongcong.fchat.ui.main

import android.support.v4.app.Fragment
import android.text.Editable
import android.view.MenuItem
import android.view.View
import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_main.*
import vn.phuongcong.adpater.ViewPagerAdapter
import vn.phuongcong.fchat.App
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.R.id.*
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.main.fragment.addfriend.NewChatFragment
import vn.phuongcong.fchat.ui.main.fragment.listfriend.FriendFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupFragment
import vn.phuongcong.fchat.ui.main.fragment.listmsg.MsgFragment
import vn.phuongcong.fchat.ui.main.fragment.profile.ProfileFragment
import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter
    private var timer = Timer()
    private val DELAY: Long = 2000
    private var curentFragment: Int? = null;
    override val contentLayoutID: Int
        get() = R.layout.activity_main

    override fun injectDependence() {
        (application as App).component.plus(ViewModule(this)).injectTo(this)

    }

    override fun initData() {
        editText.textWatcher {
            beforeTextChanged { text, start, count, after -> }
            onTextChanged { text, start, before, count -> if (timer != null) timer.cancel(); }
            afterTextChanged { text -> if (text!!.length >= 3) after(text) }
        }
        onViewPager()


    }

    private fun onViewPager() {
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun after(text: Editable?) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                when (curentFragment) {
                    Contans.ADDFRAGMENT -> mainPresenter.onSearchUser(text.toString())
                }
            }


        }, DELAY)

    }


    override fun onClick() {

    }

//    private fun openFragment(fragment: Fragment) {
//        if (fragment != null) {
//            val transaction = supportFragmentManager.beginTransaction()
//            transaction.add(R.id.framlayout, fragment)
//            transaction.addToBackStack(null);
//            transaction.commit();
//        }
//    }

    override fun onError(string: String) {

    }

    override fun showToast(msg: String) {

    }
}




