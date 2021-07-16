package com.sign.currencyconverter.adapter

import com.sign.currencyconverter.R


class RecViewItemInitialization {

    private val recyclerViewItemsList = mutableMapOf<Int,RecyclerViewItem>()
    init {
        with(recyclerViewItemsList){
            put(R.string.usd,RecyclerViewItem(R.drawable.usa,R.string.usd, R.string.american_dollar))
            put(R.string.cad,RecyclerViewItem(R.drawable.canada,R.string.cad, R.string.canadian_dollar))
            put(R.string.uah,RecyclerViewItem(R.drawable.ukr,R.string.uah, R.string.ukrainian_hryvnia))
            put(R.string.rub,RecyclerViewItem(R.drawable.rus,R.string.rub, R.string.russian_ruble))
            put(R.string.pln,RecyclerViewItem(R.drawable.pol,R.string.pln, R.string.polish_zloty))
            put(R.string.eur,RecyclerViewItem(R.drawable.eur,R.string.eur, R.string.euro))
            put(R.string.cny,RecyclerViewItem(R.drawable.china,R.string.cny, R.string.chinese_yuan))
            put(R.string.gbp,RecyclerViewItem(R.drawable.uk_eng,R.string.gbp, R.string.british_pound))
        }
    }

    fun getListWithoutCurrentMapKey(code : Int) : List<RecyclerViewItem>{
        val list = mutableListOf<RecyclerViewItem>()
        for(key in recyclerViewItemsList.keys){
            if (key!=code){
                recyclerViewItemsList.get(key)?.let { list.add(it) }
            }
        }
        return list
    }
}