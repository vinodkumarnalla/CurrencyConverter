package com.android.vinod.currencyconverter

import android.content.Context
import com.android.vinod.data.utils.CurrencyConverter
import com.android.vinod.currencyconverter.util.AppUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.mockito.Mockito

import android.content.SharedPreferences
import com.android.vinod.domain.requests.State
import org.mockito.ArgumentMatchers.*


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyConversionUtilTest {

    @Test
     fun testCurrencyConversion(){
         val currencyConverter = CurrencyConverter()
         val result = currencyConverter.convertCurrency(1.0,2.0,4.0)
         assert(result ==8.0)
     }

    @Test
    fun testAppDatabaseSyncStatus(){
        val appUtils = AppUtils()
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context: Context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.getLong(anyString(), anyLong())).thenReturn(0)
        val state = appUtils.checkAPIState(context)
        assert(state == State.NEW)
    }

    @Test
    fun testAppDatabaseSyncStatus_withExistingData(){
        val appUtils = AppUtils()
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context: Context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.getLong(anyString(), anyLong())).thenReturn(System.currentTimeMillis()-1500000)
        val state = appUtils.checkAPIState(context)
        assert(state == State.UPDATED)
    }

    @Test
    fun testAppDatabaseSyncStatus_withUpdateData(){
        val appUtils = AppUtils()
        val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
        val context: Context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.getLong(anyString(), anyLong())).thenReturn(System.currentTimeMillis()-5500000)
        val state = appUtils.checkAPIState(context)
        assert(state == State.UPDATE)
    }

    @Test
    fun testStringToDoubleConversion(){
        val appUtils = AppUtils()
        val result = appUtils.getEnteredValue("2.0")
        assert(result == 2.0)
    }
}