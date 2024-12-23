package com.cash.finian.loan.okrd.ifectiv.common.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale




object DateUtil {

    fun formatDate(time: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date(time))
    }

    fun formatDate2(time: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(time))
    }

    fun formatDate3(time: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(time))
    }


    fun formatTrackDate(time: Long): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return sdf.format(Date(time))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getDateAfterOneWeek(): String? {
        val calendar: Calendar = Calendar.getInstance()
        // 将日期设置为当前日期
        calendar.setTimeInMillis(System.currentTimeMillis())
        // 在当前日期上增加一周，即7天
        calendar.add(Calendar.DATE, 7)

        // 格式化日期
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(calendar.getTime())
    }

}