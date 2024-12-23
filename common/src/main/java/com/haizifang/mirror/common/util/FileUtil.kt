package com.cash.finian.loan.okrd.ifectiv.common.util

import android.content.Context
import android.os.Environment

/**
 * Created by kjt on 2024/6/23
 */
object FileUtil {



    fun getShootFile(ctx: Context): String? {
        return ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.path
    }

}