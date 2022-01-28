package com.android.vinod.currencyconverter.di

import com.android.vinod.data.APIInterface
import com.android.vinod.data.DataRepositoryImplementation
import com.android.vinod.data.database.CurrencyDao
import com.android.vinod.data.mapper.AddCurrencyMapper
import com.android.vinod.data.mapper.CurrencyDaoMapper
import com.android.vinod.domain.repositary.DataRepository
import com.android.vinod.domain.usecases.GetCurrencyDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDataRepository(apiInterface: APIInterface,currencyDao: CurrencyDao,currencyDaoMapper: CurrencyDaoMapper,addCurrencyMapper: AddCurrencyMapper): DataRepository =
         DataRepositoryImplementation(apiInterface,currencyDao,currencyDaoMapper,addCurrencyMapper);

    @Provides
    @Singleton
    fun provideCurrencyDataUseCase(dataRepository: DataRepository): GetCurrencyDataUseCase =
        GetCurrencyDataUseCase(dataRepository);




}