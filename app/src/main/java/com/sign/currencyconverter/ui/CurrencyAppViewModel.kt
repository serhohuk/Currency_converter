package com.sign.currencyconverter.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sign.currencyconverter.models.Currency
import com.sign.currencyconverter.models.Results
import com.sign.currencyconverter.repository.CurrencyRepository
import com.sign.currencyconverter.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CurrencyAppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : CurrencyRepository

    val currencyRate : MutableLiveData<Resource<Results>> = MutableLiveData()

    init {
        repository = CurrencyRepository()
        getCurrentCurrencyCourse("USD_UAH")
    }

    fun getCurrentCurrencyCourse(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            safeHandleResponse(query)
        }
    }

    private fun handleResponse(response : Response<Currency>,query: String) : Resource<Results> {
        if(response.isSuccessful){
            response.body()?.let { currency->
                val resultsResponse = currency.results.get(query)
                return Resource.Success(resultsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeHandleResponse(query :String){
        try {
            val response = repository.getCurrentCurrencyCourse(query)
            currencyRate.postValue(handleResponse(response,query))
        }
        catch (t : Throwable){
            currencyRate.postValue(Resource.Error("Something went wrong"))
        }
    }

    fun roundTwoDigits(number : Double) : String{
        return String.format("%.2f",number).toDouble().toString()
    }
}