package vn.phuongcong.fchat.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ominext on 10/23/2017.
 */
class DateTimeUltil {
    companion object {
        var mTimeFormat: String = "hh : mm"
        var mCalendar: Calendar = Calendar.getInstance()
        fun getTimeCurrent(): String {
            var sdf: SimpleDateFormat = SimpleDateFormat(mTimeFormat)
            return sdf.format(mCalendar.time)
        }
    }
}