package vn.phuongcong.fchat.ui.main.fragment.chat

import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ChatView :BaseView{
    fun getListMessageSuccess(messages: MutableList<Message>)
}