package com.sign.currencyconverter.adapter

import java.io.Serializable


data class RecyclerViewItem(
    val imgDrawable : Int,
    val currencyCode : Int,
    val currencyCountry : Int) : Serializable