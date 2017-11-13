package vn.phuongcong.fchat.common.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Congphuong on 10/23/2017.
 */
class DateTimeUltil {
    companion object {
        val mTimeFormat: String = "hh : mm"
        val mTimeSFormat: String = "mm:ss"
        val mDateTimeFormat = "dd/MM/yyyy hh:mm"

        fun getTimeCurrent(): String {
            var mCalendar: Calendar = Calendar.getInstance()
            return mCalendar.timeInMillis.toString()
        }

        fun fotmatTime(timeMili: Long, strFormat: String): String {
            var sdf = SimpleDateFormat(strFormat)
            return sdf.format(timeMili)
        }

        fun convertLongToTime(time: Long): String {
            var timeString: String? = null
            var totalSecond = time / 1000
            if (totalSecond < 60) {
                timeString = "$totalSecond giây trước "
            } else {
                if (totalSecond < 3600) {
                    var totalMinutes = totalSecond / 60
                    timeString = "$totalMinutes phút trước "
                } else {
                    if (totalSecond < (3600 * 24)) {
                        var totalhour = totalSecond / 3600
                        timeString = "$totalhour giờ trước "
                    } else {
                        var totaldate = totalSecond / (3600 * 24)
                        timeString = "$totaldate ngày trước "
                    }
                }

            }
            return timeString!!
        }
    }
}