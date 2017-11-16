package vn.phuongcong.fchat.common

import android.content.Context

/**
 * Created by Ominext on 10/13/2017.
 */
object Contans {
    val SPF_NAME = "saveUser"
    val VERY_FAIL = "email chưa được xác nhận, vui lòng kiểm tra hộp thư đến "
    val ERROR_ACOUNT_EXISTED = "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account."
    val ACOUNT_EXISTED = "địa chỉ email đã được sử dụng cho một tài khoản khác ! hê hê"
    val REGIS_ACTIVITY="regisActivity"
    val REQUEST_CHECK_EMAIL="kiểm tra hộp thư đến email để xác nhận tài khoản mới"
    val  LOGIN_PASS="loginpass"
    val  LOGIN_EMAIL="loginemail"
    val KEY_FROM_ACTIVTY="activity-from"
    val KEY_PASS="pass"
    val KEY_EMAIL="email"
    val SPLASH_ACTIVITY="splashActivity"
    val PRE_USER_AVATAR="userAvatar"
    val  PRE_USER_EMAIL="prefs_email"
    val  PRE_USER_NAME="prefs_name"
    val  PRE_USER_ID="prefs_id"
    val FRIEND_PATH ="FRIEND"
    val USERS_PATH  = "USER"
    val NOT_HAVE_FRIEND="Bạn hãy thử tính năng thêm bạn bè của chúng tôi"
    val CHAT: String?="CHAT"
    val CHAT_ITEM: String?="chat_item"
    val MESSAGE_LAST: String?="message_last"
    val TIME_SEND_LAST:String?="timeLastSend"
    val MESSAGE_LASTS: String?="MESSAGELASTS"
    val MESSAGE: String?="MESSAGE"
    val CAMERA_PERMISSION_REQUEST: Int=1
    val CAMERA_PIC_REQUEST: Int=2
    val EXTERNAL_PERMISSION_REQUEST: Int=0
    val EMAIL_PATH="email"
    val PIC_CHOOSE_CODE: Int=1000
    val PIC_TAKE_CODE: Int=2000
    val IMAGE_PATH: String="IMAGE"
    val ATATAR_PATH: String="avatar"
    val NAME_PATH: String="name"
    val TITLE_ADD_FRIEND="Thêm bạn"
    val REQUEST_INPUT_EMAIL="Nhập email"
    val EMAIL_FAIL="Email không đúng định dạng"
    val NOTI_DELETE_FRIEND_SUCCESS="Xóa bạn thành công!"
    val TITLE_DELE_FRIEND="Xóa bạn"
    val REQUEST_DELE_FRIEND="Bạn có muốn xóa"
    val EMAIL_NOT_FOUND="Không tìm thấy email này"
    val EMAIL_OF_ME="Email này là của bạn"
    val ADD_FRIEND_NOT_FOUND: String="Thêm bạn không thành công!"
    val DELETE_FRIEND_NOT_FOUND: String="Xóa bạn không thành công"
    val TITLE_PASS="Mật khẩu"
    val REQUEST_INPUT_NEW_ACCOUNT="Nhập tài khoản mới"
    val TITLE_RENAME="Đổi tên"
    val PLEASE_INPUT_PSS="Nhập mật khẩu"
    val REPEAT_OLD_PASS="Phải khác mật khẩu cũ"
    val REPEATPASS_NOT_FOUND="Không trùng mật khẩu mới"
    val CHANG_PASS_NOT_FOUND="Thay đổi mật khẩu không thành công"
    val CONFIRM_CHANGE_PASS="Mật khẩu của bạn sẽ được thay đổi"
    val CHANGE_PASS_SUCCESS="Thay đổi mật khẩu thành công"
    val NAME_NOT_NULL="Tên tài khoản không được để trống"
    val OLD_NAME="Trùng với tài khoản cũ"
    val STATUS_PATH="status"
    val ISONLINE: String="isOnline"
    val TIME_STAMP: String="timeStamp"
    val TIME_TO_SFRESH: Long=10*1000
    val TIME_TO_OFFLINE:Long=60*1000
    val STATUS_OFFLINE="Cách đây "
    val STATUS_ONLINE="Đang hoạt động"
    val CHAT_PATH: String="MESSAGE"
    val IMAGE_MESSAGE: String="Image_Message"
    val HINH_ANH: String="[Hình Ảnh]"
    val SUM_MESSAGE: String?="sum message"
    val LINK_IMAGE_CURRENT: String?="link_image_current"
    val REQUEST_WRITE_STORAGE: Int=3000
    val LOADING="Vui lòng đợi..."

    val CONFRIM_CHANGE_AVATAR =" ssss"

    val CF_CHANGE_PASS =1
    val CF_CHANGE_NAME =2
    val CF_CHANGE_AVATAR =3
    val REQUEST_CONFRIM_CHANGE_ACOUNT: String="Tên tài khoản của bạn sẽ được thay đổi"
    val REQUEST_CONFRIM_CHANGE_AVATAR: String="bạn chắc chắn muốn thay đổi ảnh đại diện"
    val REQUEST_CONFRIM_CHANGE_PASS: String="Mật khẩu của bạn sẽ được thay đổi"
    val REQUEST_PERMISION_WRITE_STORAGE: String="Oops you just denied the permission"
    val CHANGE_AVATAR_SUCCESS: String="thay đổi ảnh dại diện thành công"
    val CHANGE_NAME_SUCCESS: String="Thay đổi tên thành công"
    val GROUP_FRAGMENT: Int=2
    val MSG_FRAGMENT: Int=0
    val FRIEND_FRAGMENT: Int=1
    val AM_THANH: String?="[Tin nhắn thoại]"
    val AVATAR: String?="avatar"
    val STICKER: String?="[STICKER]"
}