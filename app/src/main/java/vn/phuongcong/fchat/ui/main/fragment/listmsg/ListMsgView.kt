package vn.phuongcong.fchat.ui.main.fragment.listmsg

import vn.phuongcong.fchat.model.Messagelast
import vn.phuongcong.fchat.model.User
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ListMsgView :BaseView{

    fun OnLoadListChatSuccess(listChat: MutableList<User>)
    fun onLoadListMessagelast(listMessagelast: MutableList<Messagelast>)
    fun onDeleteChatSuccess()
    fun onDeleteChatFail(message: String?)
}