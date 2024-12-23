package com.cash.finian.loan.okrd.ifectiv.common.util

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.cash.finian.loan.okrd.ifectiv.common.R


object ToastHelper {

    private var lastToastTime = 0L

    fun showToast(context: Context, content: String) {
        try {
            if (System.currentTimeMillis() - lastToastTime < 3000) return
            lastToastTime = System.currentTimeMillis()
            //        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
            val layout = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null)
            layout.findViewById<AppCompatTextView>(R.id.message).apply {
                setText(content)
            }
            val toast = Toast(context)
            toast.duration = Toast.LENGTH_SHORT
            toast.setView(layout)
            toast.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}