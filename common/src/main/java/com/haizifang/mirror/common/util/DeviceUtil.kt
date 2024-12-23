package com.cash.finian.loan.okrd.ifectiv.common.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.util.Log


object DeviceUtil {

    fun getPackName(context: Context): String {
        return context.packageName
    }

    fun getVersionName(context: Context): String {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getVersionCode(context: Context): Int {
        try {
            return context.packageManager.getPackageInfo(context.packageName, 0).versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 1
    }

    fun getDeviceId(context: Context): String {
        try {
            if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //            val telephonyManager =
                //                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                //            if (telephonyManager != null) {
                //                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //                    telephonyManager.imei
                //                } else {
                //                    telephonyManager.deviceId
                //                }
                //            }
                val contentResolver = context.contentResolver
                return Secure.getString(contentResolver, Secure.ANDROID_ID)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private var tm: TelephonyManager? = null
    fun getDeviceId1(context: Context): String? {
        var deviceId = ""
        try {
            val androidId = Secure.getString(context.contentResolver, Secure.ANDROID_ID)
            deviceId = androidId
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (tm == null) {
                    tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        deviceId = tm!!.imei
                    }
                }
                if (deviceId.isNullOrEmpty()){
                    deviceId = tm!!.deviceId
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return deviceId
    }

    fun getAndroidVersion(): String {
        try {
            return android.os.Build.VERSION.SDK_INT.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}