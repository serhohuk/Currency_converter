package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import android.util.Log
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
import com.sign.currencyconverter.utils.ButtonResource
import com.sign.currencyconverter.utils.QueryRequest
import com.sign.currencyconverter.utils.Resource
import kotlinx.android.synthetic.main.fragment_converter.*


class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var viewModel: CurrencyAppViewModel
    val args : ConverterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<RecyclerViewItem>("key")?.observe(
            viewLifecycleOwner) { result ->
            clearEditText()
            changeCurrentCurrency(result)
        }

        from_etTypeCurrency.addTextChangedListener{editable->
                editable?.let {
                    if (editable.toString().isNotEmpty() && editable.toString()!="."){
                        to_etTypeCurrency.setText(viewModel.handleConvert(editable.toString()))
                    }
                    else{
                        clearEditText()
                    }
                }
            }
        from_currency_button.setOnClickListener {
            val buttonResource = ButtonResource(to_currency_button.id,to_currency_country_code.text.toString())
            viewModel.currencyCodeButtonRecView.value = buttonResource
            viewModel.clickedButtonResource.value = from_currency_country_code.text.toString()
            findNavController().navigate(R.id.action_converterFragment_to_listCurrenciesFragment)
        }

        to_currency_button.setOnClickListener {
            val buttonResource = ButtonResource(from_currency_button.id,from_currency_country_code.text.toString())
            viewModel.currencyCodeButtonRecView.value = buttonResource
            viewModel.clickedButtonResource.value = to_currency_country_code.text.toString()
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

    fun changeCurrentCurrency(resultItem : RecyclerViewItem?) {
        resultItem?.let { selectedItem->
            viewModel.currencyCodeButtonRecView.observe(viewLifecycleOwner, Observer { savedButtonState->
                val currencyChangeButtonData = viewModel.recViewItems.getListWithCurrentKey(savedButtonState.currencyCode)
                if (savedButtonState.buttonID==from_currency_button.id){
                    setDataForFromButton(currencyChangeButtonData)
                }
                else{
                    setDataForToButton(currencyChangeButtonData)
                }
            })
            when (viewModel.currencyCodeButtonRecView.value?.currencyCode) {
                from_currency_country_code.text -> {
                    setDataForToButton(selectedItem)
                }
                to_currency_country_code.text -> {
                    setDataForFromButton(selectedItem)
                }
            }
        }
        sendRequestToServer()
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


    fun setDataForFromButton(data : RecyclerViewItem){
        from_currency_country_code.text = getString(data.currencyCode)
        from_currency_name.text = getString(data.currencyCountry)
        from_imv.setImageDrawable(resources.getDrawable(data.imgDrawable, null))
    }

    fun setDataForToButton(data : RecyclerViewItem){
        to_currency_country_code.text = getString(data.currencyCode)
        to_currency_name.text = getString(data.currencyCountry)
        to_imv.setImageDrawable(resources.getDrawable(data.imgDrawable, null))
    }

    fun sendRequestToServer(){
        val queryServerRequest = QueryRequest(from = from_currency_country_code.text.toString(),
            to=to_currency_country_code.text.toString()).toString()
        viewModel.getCurrentCurrencyCourse(queryServerRequest)
    }

    fun clearEditText(){
        from_etTypeCurrency.text.clear()
        to_etTypeCurrency.text.clear()
    }
}