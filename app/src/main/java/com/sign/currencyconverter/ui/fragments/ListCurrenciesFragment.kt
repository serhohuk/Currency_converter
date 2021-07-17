package com.sign.currencyconverter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sign.currencyconverter.R
import com.sign.currencyconverter.adapter.MyRecyclerViewAdapter
import com.sign.currencyconverter.ui.CurrencyAppViewModel
import com.sign.currencyconverter.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_list_currencies.*


class ListCurrenciesFragment : Fragment(R.layout.fragment_list_currencies) {

    private lateinit var myadapter : MyRecyclerViewAdapter
    private lateinit var viewModel: CurrencyAppViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        myadapter.setOnItemClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("key", it)
            findNavController().popBackStack()
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val buttonKey = viewModel.clickedButtonResource.value as String
                val recViewItem = viewModel.recViewItems.getListWithCurrentKey(buttonKey)
                findNavController().previousBackStackEntry?.savedStateHandle?.set("key", recViewItem)
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    fun setupRecyclerView(){
        val list = viewModel.recViewItems.getListWithoutCurrentMapKey(viewModel.currencyCodeButtonRecView.value?.currencyCode!!)
        myadapter = MyRecyclerViewAdapter(list)
        rec_view.apply {
            adapter = myadapter
            layoutManager = LinearLayoutManager(activity)

        }
    }
}