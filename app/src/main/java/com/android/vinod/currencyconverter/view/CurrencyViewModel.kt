package com.android.vinod.currencyconverter.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.vinod.domain.Result
import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.domain.requests.ApiRequest
import com.android.vinod.domain.requests.State
import com.android.vinod.domain.usecases.GetCurrencyDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val getCurrencyDataUseCase: GetCurrencyDataUseCase) :
    ViewModel() {

    private var resultLiveData = MutableLiveData<List<CurrencyModel>>()
    private var errorLiveData = MutableLiveData<String>()
    private var progressLiveData = MutableLiveData<Boolean>()
    private var updateDateLiveData = MutableLiveData<Boolean>()

    fun getCurrencies(value: Double, source: String, state: State) {
        progressLiveData.postValue(true)
        viewModelScope.async {
            try {
                val result = getCurrencyDataUseCase.execute(ApiRequest(value, state, source))
                progressLiveData.postValue(false)
                when (result) {
                    is Result.Success -> {
                        if (state != State.UPDATED) {
                            updateDateLiveData.postValue(true)
                        }
                        resultLiveData.postValue(result.value);

                    }
                    is Result.Failure -> {
                        errorLiveData.postValue(result.message.message)
                    }
                }

            } catch (e: Exception) {
                progressLiveData.postValue(false)
                errorLiveData.postValue(e.message)
            }
        }
    }

    fun getResultObserver(): LiveData<List<CurrencyModel>> {
        return resultLiveData;
    }

    fun getErrorObserver(): LiveData<String> {
        return errorLiveData;
    }

    fun getProgressObserver(): LiveData<Boolean> {
        return progressLiveData;
    }

    fun getUpdateStatusLiveData(): LiveData<Boolean> {
        return updateDateLiveData
    }
}