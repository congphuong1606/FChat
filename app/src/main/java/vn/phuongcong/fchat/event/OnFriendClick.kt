package vn.phuongcong.fchat.event

import vn.phuongcong.fchat.data.User

/**
 * Created by Ominext on 10/19/2017.
 */
interface OnFriendClick {
    fun onItemClick(user: User)
}