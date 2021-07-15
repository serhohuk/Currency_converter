package com.sign.currencyconverter.api

import com.sign.currencyconverter.models.Currency
import com.sign.currencyconverter.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterAPI {

    @GET("api/v7/convert")
    suspend fun getCurrentCurrencyCourse(
        @Query("apiKey")
        apiKey : String = API_KEY,
        @Query("q")
        query : String,
        @Query("compact")
        isCompact : String = "n"
    ) : Response<Currency>




}