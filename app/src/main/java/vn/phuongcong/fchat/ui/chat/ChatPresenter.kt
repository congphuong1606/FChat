package vn.phuongcong.fchat.ui.chat

import android.graphics.Bitmap
import vn.phuongcong.fchat.data.model.Chat

/**
 * Created by Ominext on 11/7/2017.
 */
class ChatPresenter {

    fun getListChat(mChatItem: Chat) {}
    fun sendImageFromStorage(mListPathCurrent: MutableList<String>) {}
    fun sendImageCamera(image: Bitmap) {}
    fun getListImage(chatActivity: ChatActivity) {}
    fun sendMessagetext(messagetext: String, mChatItem: Chat) {}
    fun sendMessageImage(linkImage: MutableList<String>, mChatItem: Chat) {}
    fun sendMessageImageCamera(downloadUrl: String?, mChatItem: Chat) {}
}