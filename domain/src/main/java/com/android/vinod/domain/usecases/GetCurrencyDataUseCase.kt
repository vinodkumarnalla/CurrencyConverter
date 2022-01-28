package com.android.vinod.domain.usecases

import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.domain.repositary.DataRepository
import com.android.vinod.domain.requests.ApiRequest
import javax.inject.Inject
import com.android.vinod.domain.Result

 class GetCurrencyDataUseCase @Inject constructor(private val dataRepo: DataRepository) {

      suspend fun execute(request: ApiRequest): Result<List<CurrencyModel>>{
        return dataRepo.getCurrencyInformation(request)
    }
}