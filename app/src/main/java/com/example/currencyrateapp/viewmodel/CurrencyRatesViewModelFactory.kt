package com.example.currencyrateapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyRatesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyRatesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyRatesViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}