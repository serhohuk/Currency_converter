package com.sign.currencyconverter.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sign.currencyconverter.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyAppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : CurrencyRepository

    val TAG = "SAMETON"
    val currencyRate : MutableLiveData<Double> = MutableLiveData()

    init {
        repository = CurrencyRepository()
        getCurrentCurrencyCourse("USD_UAH")
    }


    fun getCurrentCurrencyCourse(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCurrentCurrencyCourse(query)
            val cur = response.body()
            val result = cur?.results?.get(query)
            currencyRate.postValue(result?.value)
        }
    }



    fun setDataInfo() {

    }
}