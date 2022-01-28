package com.android.vinod.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.vinod.data.database.CurrencyDao

@Entity(tableName = CurrencyDao.CURRENCY_TABLE)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name = CurrencyDao.COUNTRY_CURRENCY) val countryCurrency: String,
    @ColumnInfo(name = CurrencyDao.VALUE) val value: Double,
)