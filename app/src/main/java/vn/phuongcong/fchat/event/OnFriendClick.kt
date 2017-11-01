package vn.phuongcong.fchat.event

import vn.phuongcong.fchat.model.User

/**
 * Created by Ominext on 10/19/2017.
 */
interface OnFriendClick {
    fun onItemClick(user: User)
    fun onLongItemClick(user: User, adapterPosition: Int)
}