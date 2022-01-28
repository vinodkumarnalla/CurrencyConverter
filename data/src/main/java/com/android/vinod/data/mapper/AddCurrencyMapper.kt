package com.android.vinod.data.mapper

import com.android.vinod.data.entities.CurrencyEntity
import com.android.vinod.data.entities.ResponseData
import javax.inject.Inject

class AddCurrencyMapper @Inject constructor() : BaseMapper<ResponseData?, List<CurrencyEntity>> {


    override fun transform(data: ResponseData?): List<CurrencyEntity> {
        val list = arrayListOf<CurrencyEntity>()
        data?.quotes?.forEach() { entry ->
            list.add(CurrencyEntity(value = entry.value, countryCurrency = entry.key.substring(3)))
        }
        return list;
    }
}