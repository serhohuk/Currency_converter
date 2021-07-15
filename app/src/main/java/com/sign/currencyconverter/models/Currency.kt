package com.sign.currencyconverter.models

data class Currency(
    val query: Query,
    val results: Map<String, Results>
)