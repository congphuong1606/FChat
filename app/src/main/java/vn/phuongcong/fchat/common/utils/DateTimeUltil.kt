package vn.phuongcong.fchat.common.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ominext on 10/23/2017.
 */
class DateTimeUltil {
    companion object {
        var mTimeFormat: String = "hh : mm"

        fun getTimeCurrent(): String {
            var mCalendar: Calendar = Calendar.getInstance()
            return mCalendar.timeInMillis.toString()
        }

        fun fotmatTime(timeMili: Long, strFormat: String): String {
            var sdf = SimpleDateFormat(strFormat)
            return sdf.format(timeMili)
        }
    }
}