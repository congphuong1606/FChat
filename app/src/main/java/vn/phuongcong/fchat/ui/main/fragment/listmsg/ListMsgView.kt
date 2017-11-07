package vn.phuongcong.fchat.ui.main.fragment.listmsg

import vn.phuongcong.fchat.data.model.Messagelast
import vn.phuongcong.fchat.data.model.User
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ListMsgView :BaseView{

    fun OnLoadListChatSuccess(listChat: MutableList<User>)
    fun onLoadListMessagelast(listMessagelast: MutableList<Messagelast>)
}