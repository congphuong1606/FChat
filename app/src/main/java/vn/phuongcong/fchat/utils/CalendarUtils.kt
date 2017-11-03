package vn.phuongcong.fchat.utils

import java.util.*


/**
 * Created by Administrator on 10/24/2017.
 */
class CalendarUtils {
    companion object {
        fun currentTime(): String {
            var calendar: Calendar = Calendar.getInstance();
            return "" + calendar.get(Calendar.SECOND) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.HOUR)
        }

        fun currentDate(): String {
            var calendar: Calendar = Calendar.getInstance();
            return "" + calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR)
        }
    }
}