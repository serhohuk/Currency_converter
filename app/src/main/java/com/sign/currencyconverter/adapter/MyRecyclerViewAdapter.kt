package com.sign.currencyconverter.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sign.currencyconverter.R
import kotlinx.android.synthetic.main.recycler_view_layout.view.*

class MyRecyclerViewAdapter(items : List<RecyclerViewItem>) : RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>(){

    private var listItems : List<RecyclerViewItem>? = null

    init {
        listItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currency = listItems?.get(position) as RecyclerViewItem
        holder.itemView.apply {
            rv_currency_country_code.text = resources.getString(currency.currencyCode)
            rv_currency_name.text = resources.getString(currency.currencyCountry)
            rv_imv.setImageDrawable(resources.getDrawable(currency.imgDrawable,null))
        }
    }

    override fun getItemCount(): Int {
        return listItems?.size as Int
    }

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

}