package com.sign.currencyconverter.repository

import com.sign.currencyconverter.api.RetrofitInstance

class CurrencyRepository {

    suspend fun getCurrentCurrencyCourse(query : String)  =
        RetrofitInstance.api.getCurrentCurrencyCourse(query= query)
}