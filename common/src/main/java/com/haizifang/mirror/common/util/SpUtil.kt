package com.cash.finian.loan.okrd.ifectiv.common.util

import android.content.Context

object SpUtil {
    private const val SP_NAME = "spConfig"


    @JvmStatic
    fun saveString(context: Context, key: String?, value: String?): Boolean {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.edit().putString(key, value).commit()
    }

    @JvmStatic
    fun getString(context: Context, key: String?): String? {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key,"")
    }

    @JvmStatic
    fun getBoolean(context: Context, key: String?, defvalue: Boolean): Boolean {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, defvalue)
    }


    @JvmStatic
    fun saveBoolean(context: Context, key: String?, value: Boolean): Boolean {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.edit().putBoolean(key, value).commit()
    }

    @JvmStatic
    fun getInt(context: Context, key: String?, defvalue: Int): Int {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defvalue)
    }

    @JvmStatic
    fun saveInt(context: Context, key: String?, value: Int): Boolean {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.edit().putInt(key, value).commit()
    }

    @JvmStatic
    fun saveLong(context: Context, key: String?, value: Long): Boolean {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.edit().putLong(key, value).commit()
    }

    @JvmStatic
    fun getLong(context: Context, key: String?): Long {
        val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0)
    }
}
