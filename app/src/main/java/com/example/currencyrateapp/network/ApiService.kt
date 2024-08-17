package com.example.currencyrateapp.network

import com.example.currencyrateapp.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml")
    fun getCurrencyRates(): Call<ResponseModel>
}