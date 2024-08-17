package com.example.currencyrateapp.network

import retrofit2.Converter
import java.io.InputStream
import java.io.StringReader

class XmlFileConverter<T> : Converter<InputStream, T> {

    private val xmlResponseBodyConverter = XmlResponseBodyConverter<T>()

    override fun convert(value: InputStream): T? {
        val reader = StringReader(value.bufferedReader().use { it.readText() })
        return xmlResponseBodyConverter.convert(okhttp3.ResponseBody.create(null, reader.readText()))
    }
}
