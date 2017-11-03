package vn.phuongcong.fchat.adpater

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import vn.phuongcong.fchat.ui.main.fragment.listfriend.FriendFragment
import vn.phuongcong.fchat.ui.main.fragment.listgroup.GroupFragment
import vn.phuongcong.fchat.ui.main.fragment.listmsg.MsgFragment


/**
 * Created by Ominext on 10/17/2017.
 */
class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if(position==1)
            return MsgFragment()
        if(position==2)
            return FriendFragment()
        else
        return GroupFragment()
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        when (position) {
            0 -> title = "tin nhắn"
            1 -> title = "bạn bè"
            2 -> title = "nhóm"
        }
        return title
    }
}