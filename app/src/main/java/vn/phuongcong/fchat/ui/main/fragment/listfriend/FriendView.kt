package vn.phuongcong.fchat.ui.main.fragment.listfriend


import vn.phuongcong.fchat.data.model.User
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface FriendView :BaseView{
    fun onLoadFriendsSuccess(friend: User)
    fun onAddFriendSuccessful()
    fun onDeleteFriendSuccess()

}