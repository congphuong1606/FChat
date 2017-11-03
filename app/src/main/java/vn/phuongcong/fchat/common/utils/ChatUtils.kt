package vn.phuongcong.fchat.common.utils

/**
 * Created by Ominext on 11/2/2017.
 */
object ChatUtils{

    // tạo duy nhất 1 idRoom chat theo 2 id bất kỳ
    fun getIdRoomChat(uId: String, friendId: String): String {
        if (friendId.compareTo(uId) > 0){
            return ((uId + friendId).hashCode().toString())
        } else return ((friendId + uId).hashCode().toString())
    }

}