package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sign.currencyconverter.R
import com.sign.currencyconverter.adapter.RecyclerViewItem
import com.sign.currencyconverter.ui.CurrencyAppViewModel
import com.sign.currencyconverter.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_converter.*


class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var viewModel: CurrencyAppViewModel
    val args : ConverterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        val currencyViewItemResources = args.currencyItem
        changecurrentCurrency(currencyViewItemResources)



        from_currency_button.setOnClickListener {
            viewModel.currencyCodeForRecView.value = to_currency_country_code.text.toString()
            findNavController().navigate(R.id.action_converterFragment_to_listCurrenciesFragment)
        }

        to_currency_button.setOnClickListener {
            viewModel.currencyCodeForRecView.value = from_currency_country_code.text.toString()
            findNavController().navigate(R.id.action_converterFragment_to_listCurrenciesFragment)
        }

        btn_switch.setOnClickListener {
            from_currency_country_code.text = to_currency_country_code.text
                .also { to_currency_country_code.text = from_currency_country_code.text }
            from_currency_name.text = to_currency_name.text
                .also { to_currency_name.text = from_currency_name.text }
            val fromImageViewDrawable = from_imv.drawable
            from_imv.setImageDrawable(to_imv.drawable)
                .also { to_imv.setImageDrawable(fromImageViewDrawable) }
        }
    }

    fun changecurrentCurrency(resultItem : RecyclerViewItem?) {
        resultItem?.let {
            when (viewModel.currencyCodeForRecView.value) {
                from_currency_country_code.text -> {
                    with(resultItem) {
                        to_currency_country_code.text = getString(currencyCode)
                        to_currency_name.text = getString(currencyCountry)
                        to_imv.setImageDrawable(resources.getDrawable(imgDrawable, null))
                    }
                }
                to_currency_country_code.text -> {
                    with(resultItem) {
                        from_currency_country_code.text = getString(currencyCode)
                        from_currency_name.text = getString(currencyCountry)
                        from_imv.setImageDrawable(resources.getDrawable(imgDrawable, null))
                    }
                }
                else -> {
                }
            }
        }
    }
}