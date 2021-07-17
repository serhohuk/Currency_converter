package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sign.currencyconverter.R
import com.sign.currencyconverter.adapter.RecyclerViewItem
import com.sign.currencyconverter.ui.CurrencyAppViewModel
import com.sign.currencyconverter.ui.MainActivity
import com.sign.currencyconverter.utils.QueryRequest
import com.sign.currencyconverter.utils.Resource
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var viewModel: CurrencyAppViewModel
    val args : ConverterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        val currencyViewItemResources = args.currencyItem
        changecurrentCurrency(currencyViewItemResources)


        var job : Job? = null
        from_etTypeCurrency.addTextChangedListener{editable->
            job?.cancel()
            job = MainScope().launch {
                delay(200L)
                editable?.let {
                    if (editable.toString().isNotEmpty() && editable.toString()!="."){
                        to_etTypeCurrency.setText(viewModel.handleConvert(editable.toString()))
                    }
                }
            }

        }



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
            viewModel.getCurrentCurrencyCourse(QueryRequest(from = from_currency_country_code.text.toString(),
                to=to_currency_country_code.text.toString()).toString())

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
        viewModel.getCurrentCurrencyCourse(QueryRequest(from = from_currency_country_code.text.toString(),
            to=to_currency_country_code.text.toString()).toString())

    }

    fun requestSuccessfullOrNot(){
    viewModel.currencyRate.observe(viewLifecycleOwner, Observer { results->
        when(results){
            is Resource.Error -> {
                Toast.makeText(context,results.message,Toast.LENGTH_SHORT).show()
            }
        }
    })

}
}