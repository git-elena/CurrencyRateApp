package com.example.currencyrateapp.model

data class ResponseModel(
    val banka: String,
    val datum: String,
    val poradi: Int,
    var tabulka: Tabulka
)
