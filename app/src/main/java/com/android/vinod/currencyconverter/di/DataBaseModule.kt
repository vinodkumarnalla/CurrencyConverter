package com.android.vinod.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.android.vinod.data.database.AppDatabase
import com.android.vinod.data.database.CurrencyDao
import com.android.vinod.currencyconverter.util.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase( @ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppUtils.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(appDatabase: AppDatabase): CurrencyDao {
        return appDatabase.currencyDao()
    }
}
