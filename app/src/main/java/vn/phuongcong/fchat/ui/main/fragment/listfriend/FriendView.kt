package vn.phuongcong.fchat.ui.main.fragment.listfriend

import vn.phuongcong.fchat.data.User
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface FriendView :BaseView{
    fun onLoadFriendsSuccess(friend: User)

}