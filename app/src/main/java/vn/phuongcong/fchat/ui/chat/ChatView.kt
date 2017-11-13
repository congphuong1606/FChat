package vn.phuongcong.fchat.ui.chat

import android.widget.ImageView
import vn.phuongcong.fchat.model.Message
import vn.phuongcong.fchat.ui.base.BaseView

/**
 * Created by Ominext on 10/18/2017.
 */
interface ChatView :BaseView{
    fun getListMessageSuccess(messages: MutableList<Message>)
    fun getListImageSuccess(absolutePathOfImage: MutableList<String>)
    fun sendImageSuccess(linkImage: MutableList<String>)
    fun sendImageCamereSuccess(downloadUrl: String?)
    fun sendAudioSuccess(downloadUrl: String)
    fun isPlaying(imgPlayPause: ImageView)
    fun isStop(imgPlayPause: ImageView)

}