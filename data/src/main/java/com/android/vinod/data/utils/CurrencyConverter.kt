package com.android.vinod.data.utils

import javax.inject.Inject

class CurrencyConverter @Inject constructor() {
    fun convertCurrency(source: Double, currentCountryRate: Double, enteredValue: Double): Double {
      return (currentCountryRate/source)*enteredValue
    }


}