package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sign.currencyconverter.R
import com.sign.currencyconverter.adapter.MyRecyclerViewAdapter
import com.sign.currencyconverter.adapter.RecViewItemInitialization
import com.sign.currencyconverter.ui.CurrencyAppViewModel
import com.sign.currencyconverter.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.fragment_list_currencies.*


class ListCurrenciesFragment : Fragment(R.layout.fragment_list_currencies) {

    private lateinit var myadapter : MyRecyclerViewAdapter
    private lateinit var viewModel: CurrencyAppViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
    }

    fun setupRecyclerView(){
        val recViewItems = RecViewItemInitialization()
        val list = recViewItems.getListWithoutCurrentMapKey(R.string.usd)
        myadapter = MyRecyclerViewAdapter(list)
        rec_view.apply {
            adapter = myadapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

}