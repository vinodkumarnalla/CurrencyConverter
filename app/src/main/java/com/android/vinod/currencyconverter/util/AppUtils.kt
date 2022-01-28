package com.android.vinod.currencyconverter.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.android.vinod.domain.requests.State


class AppUtils {

    companion object {
        const val PREF_NAME = "currency_pref"
        const val LAST_UPDATED_TIME = "last_updated_time"
        const val DB_NAME = "currency.db"
    }

    fun saveLastUpdatedTime(context: Context) {
        val currentTime = System.currentTimeMillis()
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        sharedPreferences.edit().putLong(LAST_UPDATED_TIME, currentTime).apply()
    }

    private fun getLastUpdatedTime(context: Context): Long {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return sharedPreferences.getLong(LAST_UPDATED_TIME, 0)
    }

    fun checkAPIState(context: Context): State {
        val savedTime = getLastUpdatedTime(context)
        if (savedTime == 0L) {
            return State.NEW
        }
        val currentTime = System.currentTimeMillis()
        val millis: Long = currentTime - savedTime
        val mins = (millis / (1000 * 60) % 60)
        if (mins > 30) {
            return State.UPDATE
        }
        return State.UPDATED
    }

    fun getEnteredValue(text: String): Double {
        var value = 1.0
        if (text.isNotEmpty()) try {
            value = text.toDouble()
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return value
    }
}