package vn.phuongcong.fchat.ui.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import vn.phuongcong.fchat.R
import vn.phuongcong.fchat.R.id.fabMenu
import vn.phuongcong.fchat.ui.main.fragment.addFriend.AddFriendFragment
import vn.phuongcong.fchat.ui.main.fragment.addFriend.NewChatFragment
import vn.phuongcong.fchat.ui.main.fragment.listfriend.FriendFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupFragment
import vn.phuongcong.fchat.ui.main.fragment.listmsg.MsgFragment
import vn.phuongcong.fchat.ui.main.fragment.profile.ProfileFragment
import vn.phuongcong.fchattranslate.ui.base.BaseActivity


class MainActivity : BaseActivity() {


    override val contentLayoutID: Int
        get() = R.layout.activity_main

    override fun injectDependence() {

    }

    override fun initData() {

    }

    override fun onClick() {
        fabMenu.setOnClickListener {
            if (fabMenu.isOpened()) {
                fabMenu.close(true);
            }
        }
        fNewChat.setOnClickListener { openFragment(NewChatFragment()) }
        fNewChat.setOnClickListener { openFragment(AddFriendFragment()) }
        bottomNavigation.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_mesage -> openFragment(MsgFragment())
                R.id.action_friend -> openFragment(FriendFragment())
                R.id.action_chat_group -> openFragment(GroupFragment())
                R.id.action_profile -> openFragment(ProfileFragment())
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        if (fragment != null) {
            fabMenu.close(true);
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.framlayout, fragment)
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}




