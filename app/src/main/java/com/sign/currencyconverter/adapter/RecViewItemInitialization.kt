package com.sign.currencyconverter.adapter

import com.sign.currencyconverter.R


class RecViewItemInitialization {



    private val recyclerViewItemsList = mutableMapOf<String,RecyclerViewItem>()
    init {
        with(recyclerViewItemsList){
            put("USD",RecyclerViewItem(R.drawable.usa,R.string.usd, R.string.american_dollar))
            put("CAD",RecyclerViewItem(R.drawable.canada,R.string.cad, R.string.canadian_dollar))
            put("UAH",RecyclerViewItem(R.drawable.ukr,R.string.uah, R.string.ukrainian_hryvnia))
            put("RUB",RecyclerViewItem(R.drawable.rus,R.string.rub, R.string.russian_ruble))
            put("PLN",RecyclerViewItem(R.drawable.pol,R.string.pln, R.string.polish_zloty))
            put("EUR",RecyclerViewItem(R.drawable.eur,R.string.eur, R.string.euro))
            put("CNY",RecyclerViewItem(R.drawable.china,R.string.cny, R.string.chinese_yuan))
            put("GBP",RecyclerViewItem(R.drawable.uk_eng,R.string.gbp, R.string.british_pound))
        }
    }

    fun getListWithoutCurrentMapKey(code : String) : List<RecyclerViewItem>{
        val list = mutableListOf<RecyclerViewItem>()
        for(key in recyclerViewItemsList.keys){
            if (key!=code){
                recyclerViewItemsList.get(key)?.let { list.add(it) }
            }
        }
        return list
    }

    fun getListWithCurrentKey(code: String) : RecyclerViewItem{
        return recyclerViewItemsList.get(code) as RecyclerViewItem
    }
}