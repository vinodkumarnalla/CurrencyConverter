package com.android.vinod.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.vinod.data.entities.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}