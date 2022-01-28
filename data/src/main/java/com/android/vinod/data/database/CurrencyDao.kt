package com.android.vinod.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.vinod.data.entities.CurrencyEntity

@Dao
interface CurrencyDao {

    companion object {
        const val CURRENCY_TABLE = "currency_table"
        const val PRIMARY_KEY ="id"
        const val COUNTRY_CURRENCY = "country_currency"
        const val VALUE = "value"
    }

    @Insert
    suspend fun upsertCurrency(currency: CurrencyEntity)

    @Transaction
    suspend fun upsertCurrencies(currencies: List<CurrencyEntity>) {
        currencies.forEach { upsertCurrency(it) }
    }

    @Transaction
    suspend fun updateExchangeRates(currencies: List<CurrencyEntity>) {
        currencies.forEach { updateExchangeRate(it.countryCurrency, it.value) }
    }

    @Query("UPDATE CURRENCY_TABLE SET VALUE = :exchangeRate WHERE COUNTRY_CURRENCY = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Query("SELECT * FROM $CURRENCY_TABLE")
    fun getAll(): List<CurrencyEntity>

    @Query("SELECT $VALUE FROM $CURRENCY_TABLE WHERE $COUNTRY_CURRENCY = :currency")
    fun getValue(currency: String): Double

    @Query("DELETE FROM $CURRENCY_TABLE")
    fun deleteAll()

}