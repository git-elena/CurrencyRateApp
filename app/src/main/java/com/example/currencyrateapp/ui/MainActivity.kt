package com.example.currencyrateapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyrateapp.R
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrateapp.viewmodel.CurrencyRatesViewModel
import com.example.currencyrateapp.viewmodel.CurrencyRatesViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyRatesViewModel
    private lateinit var currencyRatesAdapter: CurrencyRatesAdapter

    private lateinit var dateTextView: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        findViewById<ImageButton>(R.id.btnRefresh).setOnClickListener {
            refreshData()
        }

        findViewById<ImageButton>(R.id.btnPerson).setOnClickListener {
            // Create an intent to start UserFormActivity
            val intent = Intent(this, UserFormActivity::class.java)
            startActivity(intent)
        }

        // Initialize UI components
        dateTextView = findViewById(R.id.tv_date)
//        refreshLayout = findViewById(R.id.ll_date_refresh)


        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        currencyRatesAdapter = CurrencyRatesAdapter()
        recyclerView.adapter = currencyRatesAdapter

        // Initialize ViewModel
        viewModel = ViewModelProvider(this, CurrencyRatesViewModelFactory(this))[CurrencyRatesViewModel::class.java]
        // Observe currency rates data
        viewModel.currencyRates.observe(this) { currencyRates ->
            // Update UI with currency rates data
//            refreshLayout.visibility = View.VISIBLE
            dateTextView.text = currencyRates?.datum ?: "error"

            recyclerView.visibility = View.VISIBLE

            currencyRatesAdapter.submitList(currencyRates?.tabulka?.radek ?: emptyList())

            progressBar.visibility = View.GONE
            errorTextView.visibility = View.GONE
            Log.d("MainActivity", "Fetched: $currencyRates")
        }

        // Observe errors
        viewModel.error.observe(this) { errorMessage ->
            // Handle error
//            refreshLayout.visibility = View.GONE
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            errorTextView.visibility = View.VISIBLE
            errorTextView.text = errorMessage
            Log.e("MainActivity", "Error: $errorMessage")
        }

        // Show loading indicator until the data is fetched
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE
//        refreshLayout.visibility = View.GONE
    }

    // Method to refresh data in RecyclerView
    private fun refreshData() {
        // Show loading indicator while refreshing data
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        errorTextView.visibility = View.GONE
//        refreshLayout.visibility = View.GONE

        // Trigger data fetch in ViewModel
        viewModel.fetchCurrencyRates()
    }
}
