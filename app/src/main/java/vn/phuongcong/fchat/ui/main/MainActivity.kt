package vn.phuongcong.fchat.ui.main


import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import com.bumptech.glide.Glide

import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_main.*
import vn.phuongcong.fchat.App

import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.service.OnlineService
import vn.phuongcong.fchat.ui.adapter.ViewPagerAdapter
import vn.phuongcong.fchat.ui.profile.ProfileActivity

import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject
import android.view.View
import vn.phuongcong.fchat.event.OnFabClick

import android.support.design.widget.TabLayout
import vn.phuongcong.fchat.R.id.*
import vn.phuongcong.fchat.common.utils.KeyboardUtils


class MainActivity : BaseActivity(), MainView {


    @Inject
    lateinit var sPref: SharedPreferences
    @Inject
    lateinit var presenter: MainPresenter

    private var timer = Timer()
    private val DELAY: Long = 2000
    override val contentLayoutID: Int
        get() = R.layout.activity_main


    override fun injectDependence() {
        (application as App).component.plus(ViewModule(this)).injectTo(this)
    }

    override fun initData() {
        edt_search.isFocusable = false
        KeyboardUtils.hideKeyboard(this, edt_search)
        edt_search.textWatcher {
            beforeTextChanged { text, start, count, after -> }
            onTextChanged { text, start, before, count -> if (timer != null) timer.cancel(); }
            afterTextChanged { text -> if (text!!.length >= 3) after(text) }
        }
        onViewPager()
        val uAvatar = sPref.getString(Contans.PRE_USER_AVATAR, "")
        Glide.with(this).load(uAvatar).error(resources.getDrawable(R.drawable.ic_no_image)).into(profileAction)
        val myIntent = Intent(this, OnlineService::class.java)
        startService(myIntent)
        fab.visibility = View.GONE


    }


    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    companion object {
        private var onFabClick: OnFabClick? = null
        fun setOnFabClickListenner(onFabClick: OnFabClick) {
            this.onFabClick = onFabClick
        }
    }

    private fun onViewPager() {
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun after(text: Editable?) {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                //hàm tìm kiếm user
            }


        }, DELAY)
    }


    override fun onClick() {
        profileAction.setOnClickListener { onStartActivity(ProfileActivity::class.java) }
        fab.setOnClickListener {
            onFabClick?.onFabClickListener()
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                if (position == 0) {
                    fab.visibility = View.GONE
                }
                if (position == 1) {
                    fab.visibility = View.VISIBLE
                    fab.setImageDrawable(resources.getDrawable(R.drawable.add_friend))
                }
                if (position == 2) {
                    fab.visibility = View.VISIBLE
                    fab.setImageDrawable(resources.getDrawable(R.drawable.ic_group_add_black_24dp))
                }
            }
        })
    }


}




