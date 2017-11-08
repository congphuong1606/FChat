package vn.phuongcong.fchat.model

import java.io.Serializable

/**
 * Created by Ominext on 10/27/2017.
 */
data class Messagelast(var timeLastSend:String="",var messageLast:String?="",var massageLastImage :MutableList<String> = mutableListOf() ) : Serializable