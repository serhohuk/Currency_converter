package com.sign.currencyconverter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sign.currencyconverter.R
import com.sign.currencyconverter.utils.Resource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: CurrencyAppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(CurrencyAppViewModel::class.java)

        viewModel.currencyRate.observe(this, Observer { results->
            when(results){
                is Resource.Success -> {
                    val currencyPrice = results.data?.value as Double
                    tv_now.text = viewModel.roundTwoDigits(currencyPrice)
                }
                is Resource.Error -> {
                    Toast.makeText(this,results.message,Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}