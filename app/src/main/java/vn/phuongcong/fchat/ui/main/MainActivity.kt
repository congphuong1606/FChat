package vn.phuongcong.fchat.ui.main


import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import com.bumptech.glide.Glide

import com.pawegio.kandroid.textWatcher
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import vn.phuongcong.fchat.App

import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.common.Contans
import vn.phuongcong.fchat.common.utils.ServiceUtils
import vn.phuongcong.fchat.di.module.ViewModule
import vn.phuongcong.fchat.service.OnlineService
import vn.phuongcong.fchat.ui.adapter.ViewPagerAdapter
import vn.phuongcong.fchat.ui.profile.ProfileActivity

import vn.phuongcong.fchattranslate.ui.base.BaseActivity
import java.util.*
import javax.inject.Inject


class MainActivity : BaseActivity(),MainView {

    @Inject
    lateinit var sPref:SharedPreferences
    @Inject
    lateinit var presenter:MainPresenter


    override fun onRequestFailure(string: String) {

    }

    override fun showToast(msg: String) {

    }

    private var timer = Timer()
    private val DELAY: Long = 2000
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
        val uAvatar=sPref.getString(Contans.PRE_USER_AVATAR,"")
        Glide.with(this).load(uAvatar).error(resources.getDrawable(R.drawable.ic_no_image)).into(profileAction)
        val myIntent = Intent(this, OnlineService::class.java)
        startService(myIntent)


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




