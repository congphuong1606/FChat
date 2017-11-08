package vn.phuongcong.fchat.model

import java.io.Serializable

/**
 * Created by Ominext on 10/19/2017.
 */
class Message(var senderId: String = "", val content: String? = "",
              var msgImage: MutableList<String>? = mutableListOf(),
              var timeCreate: String = "",var stickerCode:String="", var mType: Int = 0) : Serializable