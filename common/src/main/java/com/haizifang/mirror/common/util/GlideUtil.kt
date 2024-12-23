package com.cash.finian.loan.okrd.ifectiv.common.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideUtil {

    fun loadImage(context: Context, view: ImageView, url: String) {
        Glide.with(context).load(url).into(view)
    }

}