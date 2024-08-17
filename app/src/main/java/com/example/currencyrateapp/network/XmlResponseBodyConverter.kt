package com.example.currencyrateapp.network

import com.example.currencyrateapp.model.CurrencyRate
import com.example.currencyrateapp.model.ResponseModel
import com.example.currencyrateapp.model.Tabulka
import okhttp3.ResponseBody
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import retrofit2.Converter

import java.io.StringReader

class XmlResponseBodyConverter<T> : Converter<ResponseBody, T> {

    @Suppress("UNCHECKED_CAST")
    override fun convert(value: ResponseBody): T? {
        val parserFactory = XmlPullParserFactory.newInstance()
        val parser = parserFactory.newPullParser()
        parser.setInput(StringReader(value.string()))

        return parseXml(parser) as T
    }

    private fun parseXml(parser: XmlPullParser): ResponseModel? {

        var eventType = parser.eventType
        var responseModel: ResponseModel? = null
        val currencyRates = mutableListOf<CurrencyRate>()

        while (eventType != XmlPullParser.END_DOCUMENT) {
            val tagName = parser.name

            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (tagName) {
                        "kurzy" -> {
                            val banka = parser.getAttributeValue(null, "banka")
                            val datum = parser.getAttributeValue(null, "datum")
                            val poradi = parser.getAttributeValue(null, "poradi").toInt()
                            responseModel = ResponseModel(banka, datum, poradi, Tabulka(emptyList()))
                        }
                        "radek" -> {
                            val kod = parser.getAttributeValue(null, "kod")
                            val mena = parser.getAttributeValue(null, "mena")
                            val mnozstvi = parser.getAttributeValue(null, "mnozstvi").toInt()
                            val kurz = parser.getAttributeValue(null, "kurz")
                            val zeme = parser.getAttributeValue(null, "zeme")
                            val currencyRate = CurrencyRate(kod, mena, mnozstvi, kurz, zeme)
                            currencyRates.add(currencyRate)
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        responseModel?.tabulka = Tabulka(currencyRates)
        return responseModel
    }
}
