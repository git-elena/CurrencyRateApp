package com.example.currencyrateapp.network

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class XmlConverterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        return XmlResponseBodyConverter<Any>()
    }

    companion object {
        fun create(): XmlConverterFactory {
            return XmlConverterFactory()
        }
    }
}
