package vn.phuongcong.fchat.event

import android.widget.ImageView

/**
 * Created by Ominext on 10/30/2017.
 */
interface IitemClick {
    fun iClick(o: Any, img_check: ImageView?)
    fun iClick(o: Any)
}