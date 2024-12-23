package com.haizifang.mirror

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.haizifang.mirror.WMSdkManager

class SplashActivity : AppCompatActivity() {

    private val myHandler = Handler(Looper.getMainLooper(), object : Callback {
        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                1 -> {
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                }

                2 -> {
                }
            }
            return true
        }

    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.getInsetsController(window, window.decorView)
            .hide(WindowInsetsCompat.Type.statusBars())
        setContentView(R.layout.activity_splash)
        WMSdkManager.instance.init(this@SplashActivity,object : WMSdkManager.InitCallback{
            override fun initSuccess() {
                myHandler.sendEmptyMessageDelayed(1, 1000)
            }

            override fun initFailed() {
            }
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacksAndMessages(null)
    }
}