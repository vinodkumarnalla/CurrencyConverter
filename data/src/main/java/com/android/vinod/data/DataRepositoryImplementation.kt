package com.android.vinod.data

import com.android.vinod.data.database.CurrencyDao
import com.android.vinod.data.mapper.AddCurrencyMapper
import com.android.vinod.data.mapper.CurrencyDaoMapper
import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.domain.repositary.DataRepository
import com.android.vinod.domain.requests.ApiRequest
import com.android.vinod.domain.requests.State
import javax.inject.Inject
import com.android.vinod.domain.Result

class DataRepositoryImplementation @Inject constructor(
    private val api: APIInterface,
    private val currencyDao: CurrencyDao,
    private val currencyDaoMapper: CurrencyDaoMapper,
    private val addCurrencyMapper: AddCurrencyMapper
) :
    DataRepository {

    override suspend fun getCurrencyInformation(request: ApiRequest): Result<List<CurrencyModel>> {
        if (request.state == State.NEW || request.state == State.UPDATE) {
            val data = api.getData(APIConstants.API_KEY);
            if (data.isSuccessful && data.body()?.success == true) {
                val list = addCurrencyMapper.transform(data = data.body())
                if (request.state == State.NEW) {
                    currencyDao.upsertCurrencies(list)
                } else {
                    currencyDao.updateExchangeRates(list)
                }
            }
        }

        val data = currencyDao.getAll()
        return if (data !=null && data.isNotEmpty()) {
            val value = currencyDao.getValue(request.source)
            currencyDaoMapper.sourceValue = value
            currencyDaoMapper.enterValue = request.enteredNumber
            Result.Success(currencyDaoMapper.transform(data))
        } else {
            Result.Failure(Throwable(APIConstants.ERROR_MESSAGE))
        }

    }
}