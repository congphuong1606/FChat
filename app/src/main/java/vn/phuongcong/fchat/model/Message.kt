package vn.phuongcong.fchat.model

/**
 * Created by Ominext on 10/19/2017.
 */
data class Message( var senderId: String="", val content: String="", var  msgImage: String?="", var timeCreate:String="", var mType:Int=0 )