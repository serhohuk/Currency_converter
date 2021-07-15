package com.sign.currencyconverter.models

import com.google.gson.annotations.SerializedName

data class Results(
    val fr: String,
    val id: String,
    val to: String,
    @SerializedName("val")
    val value: Double
)