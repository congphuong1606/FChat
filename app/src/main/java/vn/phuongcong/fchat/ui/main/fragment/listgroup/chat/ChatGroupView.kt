package vn.phuongcong.fchat.ui.main.fragment.listgroup.chat

import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by vietcoscc on 01/11/2017.
 */
interface ChatGroupView :BaseView {
    fun showChatItem(message: Message)
}