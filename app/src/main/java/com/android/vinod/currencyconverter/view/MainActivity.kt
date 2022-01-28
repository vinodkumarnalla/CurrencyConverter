package com.android.vinod.currencyconverter.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.android.vinod.domain.models.CurrencyModel
import com.android.vinod.currencyconverter.R
import com.android.vinod.currencyconverter.databinding.ActivityMainBinding
import com.android.vinod.currencyconverter.util.AppUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TextWatcher, AdapterView.OnItemSelectedListener {


    private val currencyViewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val appUtils: AppUtils = AppUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initObservers()
        binding.etAmount.addTextChangedListener(this)
        binding.spCurrency.onItemSelectedListener = this
    }

    private fun initObservers() {
        currencyViewModel.getErrorObserver().observe(this, {
            Toast.makeText(MainActivity@ this, it, Toast.LENGTH_SHORT).show()
        })

        currencyViewModel.getProgressObserver().observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvCurrency.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvCurrency.visibility = View.VISIBLE
            }

        })

        currencyViewModel.getResultObserver().observe(this, Observer {
            setupAdapter(it)
        })

        currencyViewModel.getUpdateStatusLiveData().observe(this, Observer {
            appUtils.saveLastUpdatedTime(this@MainActivity)
        })
    }

    private fun setupAdapter(list: List<CurrencyModel>?) {
        list?.let {
            val layoutManager = GridLayoutManager(this, 2)
            binding.rvCurrency.layoutManager = layoutManager
            binding.rvCurrency.adapter = CurrencyAdapter(it)
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        refreshData()
    }

    private fun refreshData() {
        val state = appUtils.checkAPIState(this)
        val source = binding.spCurrency.selectedItem.toString()
        val text: String = binding.etAmount.text.toString()
        if (text.isNotEmpty()) {
            val value = appUtils.getEnteredValue(text)
            currencyViewModel.getCurrencies(value, source, state)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        refreshData()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}