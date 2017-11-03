package vn.phuongcong.fchat.ui.main


import android.content.SharedPreferences
import android.text.Editable

import com.pawegio.kandroid.textWatcher
import kotlinx.android.synthetic.main.activity_main.*
import vn.phuongcong.fchat.App

import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.ui.adapter.ViewPagerAdapter
import vn.phuongcong.fchat.ui.profile.ProfileActivity

import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    private var timer = Timer()
    private val DELAY: Long = 2000
    private var curentFragment: Int? = null
    override val contentLayoutID: Int
        get() = R.layout.activity_main


    override fun injectDependence() {

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
                //hàm tìm kiếm user
            }


        }, DELAY)

    }


    override fun onClick() {
        profileAction.setOnClickListener { onStartActivity(ProfileActivity::class.java) }
    }

}




