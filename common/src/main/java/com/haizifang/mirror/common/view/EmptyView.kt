package com.cash.finian.loan.okrd.ifectiv.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.cash.finian.loan.okrd.ifectiv.common.R


class EmptyView : RelativeLayout {
    constructor(context: Context?) : super(context){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init(){
        LayoutInflater.from(context).inflate(R.layout.empty_view, this, true)
    }

    fun setEmptyText(emptyText: String) {
        findViewById<TextView>(R.id.tv_empty).apply {
            text = emptyText
        }
    }

    fun setEmptyImage(@DrawableRes id: Int) {
        findViewById<ImageView>(R.id.iv_empty).apply {
            setImageResource(id)
        }
    }
}