package vn.phuongcong.fchat.ui.chat

import vn.phuongcong.fchat.data.model.Message
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ChatView :BaseView{
    fun getListMessageSuccess(messages: MutableList<Message>)
    fun getListImageSuccess(absolutePathOfImage: MutableList<String>)
    fun sendImageSuccess(linkImage: MutableList<String>)
    fun sendImageCamereSuccess(downloadUrl: String?)

}