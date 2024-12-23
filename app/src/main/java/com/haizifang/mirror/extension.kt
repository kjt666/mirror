package com.haizifang.mirror

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat


/**
 * Immerse 沉浸。设置沉浸的方式
 *
 * @param type Type.systemBars(),Type.statusBars(),Type.navigationBars()
 * @param statusIsBlack 专栏文字 true 黑色,false 白色
 * @param navigationIsBlack 导航栏按钮 true 黑色,false 白色
 * @param color 状态和导航栏的背景颜色
 */
@JvmOverloads
fun Activity.immerse(
    statusBarColor: Int = Color.BLACK
) {
    //关键代码,沉浸 false 沉浸
    WindowCompat.setDecorFitsSystemWindows(window, true)

    //设置专栏栏和导航栏的底色，透明
    window.statusBarColor = statusBarColor
    window.navigationBarColor = Color.BLACK
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.navigationBarDividerColor = Color.BLACK
    }

    //设置沉浸后专栏栏和导航字体的颜色，
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))
        ?.let { controller ->
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }
}

fun String.highLightNetAddress(): SpannableString {
    val spannableStr = SpannableString(this)
    try {
        var currentIndex = -1
        while ((this.indexOf(
                "https://",
                startIndex = currentIndex + 1
            )) != -1
        ) {
            currentIndex =
                this.indexOf("https://", startIndex = currentIndex + 1)
            val endIndex = this.indexOf(".com", startIndex = currentIndex)
            val colorSpan = ForegroundColorSpan(Color.parseColor("#59EEDE"))
            spannableStr.setSpan(
                colorSpan,
                currentIndex,
                endIndex + 4,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return spannableStr
}

/**
 * 10,000   1,000,000
 */
fun String.moneyFormat(): String {
    if (this.length < 4) return this;
    val dotCount = this.length / 3
    val charArray = this.toCharArray().toMutableList()
    for (i in 1..dotCount) {
        charArray.add(this.length - i * 3, ',')
    }
    return String(charArray.toCharArray())
}

