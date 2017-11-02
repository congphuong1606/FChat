package vn.phuongcong.fchat.model

/**
 * Created by Ominext on 11/2/2017.
 */
class FChat(val idChat: Long =System.currentTimeMillis(), var idSender:String="",
            var idReceiver: String="", var contentChat:String="",
            var imageChat: String="", var soundchat:String="", stickChat:String="", var timeChat:Long =System.currentTimeMillis())