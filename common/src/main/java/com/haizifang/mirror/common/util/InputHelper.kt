package com.cash.finian.loan.okrd.ifectiv.common.util

import android.R
import android.text.InputFilter
import android.widget.EditText


/**
 * Created by kjt on 2024/7/3
 */
object InputHelper {

    fun getNoSpaceInputFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend -> //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (source == " ") "" else null
        }
    }

    fun getLengthInputFilter(length: Int): InputFilter {
        return InputFilter.LengthFilter(length)
    }

    fun getOnlyCharAndSpaceInputFilter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isLetter(source[i]) && source[i] != ' ') {
                    return@InputFilter "" // 不是字母也不是空格，返回空字符串
                }
            }
            null // 允许输入
        }
    }
}