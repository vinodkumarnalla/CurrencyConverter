package com.android.vinod.data.mapper

import com.android.vinod.data.entities.CurrencyEntity
import com.android.vinod.data.utils.CurrencyConverter
import com.android.vinod.domain.models.CurrencyModel
import javax.inject.Inject

class CurrencyDaoMapper @Inject constructor(private val currencyConverter: CurrencyConverter) :
    BaseMapper<List<CurrencyEntity>, List<CurrencyModel>> {

    var enterValue = 1.0
    var sourceValue = 1.0
    override fun transform(data: List<CurrencyEntity>): List<CurrencyModel> {
        val list = arrayListOf<CurrencyModel>()
        data.forEach() { entry ->
            list.add(
                CurrencyModel(
                    value = currencyConverter.convertCurrency(sourceValue,entry.value,enterValue) ,
                    currency = entry.countryCurrency
                )
            )
        }
        return list
    }
}