package com.sign.currencyconverter.utils

data class QueryRequest(val from : String, val to : String){
    override fun toString(): String {
        return "${from}_${to}"
    }
}
