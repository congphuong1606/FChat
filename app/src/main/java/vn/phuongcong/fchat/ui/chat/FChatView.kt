package vn.phuongcong.fchat.ui.chat

import vn.phuongcong.fchat.model.FChat
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 11/2/2017.
 */
interface FChatView :BaseView {
    fun onSendChatSuccessful(fchat: FChat)
    fun onLoadChatsSuccess(chats: MutableList<FChat>)

}