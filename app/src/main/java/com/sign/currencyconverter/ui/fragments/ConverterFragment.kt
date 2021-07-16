package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sign.currencyconverter.R
import com.sign.currencyconverter.ui.CurrencyAppViewModel
import com.sign.currencyconverter.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_converter.*


class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var viewModel: CurrencyAppViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        from_currency_button.setOnClickListener {
            findNavController().navigate(R.id.action_converterFragment_to_listCurrenciesFragment)
        }
    }
}