package com.example.currencyrateapp.viewmodel

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyrateapp.R
import com.example.currencyrateapp.model.ResponseModel
import com.example.currencyrateapp.network.ApiService
import com.example.currencyrateapp.network.XmlConverterFactory
import com.example.currencyrateapp.network.XmlFileConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.InputStream

class CurrencyRatesViewModel(private val context: Context) : ViewModel() {
    // MutableLiveData for currency rates
    private val _currencyRates = MutableLiveData<ResponseModel?>()
    val currencyRates: LiveData<ResponseModel?> = _currencyRates

    // MutableLiveData for error handling
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // initialize Retrofit и ApiService
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cnb.cz/")
        .addConverterFactory(XmlConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    init {
        fetchCurrencyRates()
    }

    fun fetchCurrencyRates() {
        // Launch a coroutine to execute the request in the background
        viewModelScope.launch {
            try {
                apiService.getCurrencyRates().enqueue(object : Callback<ResponseModel> {
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            _currencyRates.value = response.body()
                        } else {
                            _error.value = "Error: ${response.message()}"
                            loadDefaultCurrencyRates()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        _error.value = t.localizedMessage ?: "An unknown error occurred"
                        Log.e("CurrencyRatesViewModel", "Error fetching currency rates", t)
                        loadDefaultCurrencyRates()
                    }
                })

            } catch (e: Exception) {
                // Error handling
                _error.value = e.localizedMessage ?: "An unknown error occurred."
                Log.e("CurrencyRatesViewModel", "Error fetching currency rates", e)
                loadDefaultCurrencyRates()
            }
        }
    }

    private fun loadDefaultCurrencyRates() {
        viewModelScope.launch {
            // Load XML file from resources and parse it
            val defaultRates = parseDefaultRates()

            // Set default data in LiveData
            if (defaultRates != null) {
                _currencyRates.value = defaultRates
            } else {
                _error.value = "Failed to load default currency rates."
            }
        }
    }

    private suspend fun parseDefaultRates(): ResponseModel? {
        return withContext(Dispatchers.IO) {
            try {
                // Get the input stream of the XML resource
                val inputStream: InputStream = context.resources.openRawResource(R.raw.default_currency_rates)

                // Create an instance of XmlFileConverter
                val converter = XmlFileConverter<ResponseModel>()

                // Convert InputStream to ResponseModel
                converter.convert(inputStream)

//                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
//                val parser: XmlPullParser = factory.newPullParser()
//                parser.setInput(inputStream, null)
//                // Parse XML
//                var eventType = parser.eventType
//                var responseModel: ResponseModel? = null
//                responseModel // Return ResponseModel after successful parsing
            } catch (e: Exception) {
                Log.e("CurrencyRatesViewModel", "Error parsing default currency rates", e)
                null
            }
        }
    }
}