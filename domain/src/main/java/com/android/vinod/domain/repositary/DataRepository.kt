package com.android.vinod.domain.repositary

import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.domain.requests.ApiRequest
import com.android.vinod.domain.Result

interface DataRepository {
    suspend fun getCurrencyInformation(request: ApiRequest):Result<List<CurrencyModel>>
}