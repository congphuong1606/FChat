package vn.phuongcong.fchat.data.model

import java.io.Serializable

/**
 * Created by Ominext on 10/20/2017.
 */
data class Chat( var uIdFriend:String ,var mFriend: String, var mImageFriend: String, var mLastMessage: String?, var mType: Int?) : Serializable
