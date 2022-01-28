package com.android.vinod.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.vinod.data.APIInterface
import com.android.vinod.data.DataRepositoryImplementation
import com.android.vinod.data.database.CurrencyDao
import com.android.vinod.data.mapper.AddCurrencyMapper
import com.android.vinod.data.mapper.CurrencyDaoMapper
import com.android.vinod.data.utils.CurrencyConverter
import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.domain.requests.State
import com.android.vinod.domain.usecases.GetCurrencyDataUseCase
import com.android.vinod.currencyconverter.util.MockObjects
import com.android.vinod.currencyconverter.util.TestCoroutineRule
import com.android.vinod.currencyconverter.view.CurrencyViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CurrencyTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var currencyDao: CurrencyDao

    @Mock
    private lateinit var apiResultObserver: Observer<List<CurrencyModel>>

    @Mock
    private lateinit var apiInterface: APIInterface


    @Mock
    private lateinit var apiErrorObserver: Observer<String>

    private val mockObjects = MockObjects()

    @Before
    fun setUp() {

    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            val response = mockObjects.getSuccessResponse()
            `when`(apiInterface.getData(anyString())).thenReturn(response)
            val list = mockObjects.getCurrencyList()
            `when`(currencyDao.getAll()).thenReturn(list)
            val dataRepository = DataRepositoryImplementation(
                apiInterface,
                currencyDao,
                CurrencyDaoMapper(CurrencyConverter()),
                AddCurrencyMapper()
            )
            val getCurrencyDataUseCase = GetCurrencyDataUseCase(dataRepository)
            val viewModel = CurrencyViewModel(getCurrencyDataUseCase)
            viewModel.getCurrencies(
                MockObjects.MOCK_ENTERED_NUM,
                MockObjects.MOCK_SELECTED_CURRENCY,
                State.NEW
            )
            viewModel.getResultObserver().observeForever(apiResultObserver)
            verify(apiResultObserver).onChanged(anyList())
        }
    }

    @Test
    fun givenServerResponse404_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val response = mockObjects.getErrorResponse()
            `when`(apiInterface.getData(anyString())).thenReturn(response)
            `when`(currencyDao.getAll()).thenReturn(emptyList())
            val dataRepository = DataRepositoryImplementation(
                apiInterface,
                currencyDao,
                CurrencyDaoMapper(CurrencyConverter()),
                AddCurrencyMapper()
            )
            val getCurrencyDataUseCase = GetCurrencyDataUseCase(dataRepository)
            val viewModel = CurrencyViewModel(getCurrencyDataUseCase)
            viewModel.getCurrencies(
                MockObjects.MOCK_ENTERED_NUM,
                MockObjects.MOCK_SELECTED_CURRENCY,
                State.NEW
            )
            viewModel.getErrorObserver().observeForever(apiErrorObserver)
            verify(apiErrorObserver).onChanged(anyString())
        }
    }
}