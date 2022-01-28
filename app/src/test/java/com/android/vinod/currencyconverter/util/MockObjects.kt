package com.android.vinod.currencyconverter.util

import com.android.vinod.data.entities.CurrencyEntity
import com.android.vinod.data.entities.ResponseData
import okhttp3.ResponseBody
import org.mockito.Mockito.mock

import retrofit2.Response
import retrofit2.Response.error


class MockObjects {

    companion object{
        const val MOCK_ENTERED_NUM = 1.0
        const val MOCK_SELECTED_CURRENCY = "USD"
    }

    fun getSuccessResponse(): Response<ResponseData> {
        val successResponse = ResponseData()
        successResponse.success = true
        val hashMap = HashMap<String,Double>()
        hashMap[MOCK_SELECTED_CURRENCY] = MOCK_ENTERED_NUM
        successResponse.quotes = hashMap
        return  Response.success(successResponse)
    }

    fun getErrorResponse(): Response<ResponseData> {
        return error(404, mock(ResponseBody::class.java))
    }

    fun getCurrencyList():List<CurrencyEntity>{
        val list = arrayListOf<CurrencyEntity>()
        list.add(CurrencyEntity(1, MOCK_SELECTED_CURRENCY, MOCK_ENTERED_NUM))
        return list
    }
}