package com.example.currencyrateapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyrateapp.model.CurrencyRate
import com.example.currencyrateapp.R

class CurrencyRatesAdapter : ListAdapter<CurrencyRate, CurrencyRatesAdapter.CurrencyRateViewHolder>(CurrencyRateDiffCallback())
//    (private var currencyRates: MutableList<CurrencyRate>) :
//    RecyclerView.Adapter<CurrencyRatesAdapter.CurrencyRateViewHolder>()
{

    class CurrencyRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val currencyCode: TextView = itemView.findViewById(R.id.currency_code)
        private val currencyValue: TextView = itemView.findViewById(R.id.currency_value)
        private val currencyName: TextView = itemView.findViewById(R.id.currency_name)
        private val currencyAmount: TextView = itemView.findViewById(R.id.currency_amount)
        private val currencyValueMini: TextView = itemView.findViewById(R.id.currency_value_mini)
        private val countryName: TextView = itemView.findViewById(R.id.country_name)

        fun bind(currencyRate: CurrencyRate) {
            //Binding data to view elements
            currencyCode.text = currencyRate.kod
            currencyValue.text = currencyRate.kurz
            currencyName.text = currencyRate.mena
            currencyAmount.text = currencyRate.mnozstvi.toString()
            currencyValueMini.text = currencyRate.kurz
            countryName.text = currencyRate.zeme
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency_rate, parent, false)
        return CurrencyRateViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyRateViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



    class CurrencyRateDiffCallback : DiffUtil.ItemCallback<CurrencyRate>() {
        override fun areItemsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem.kod == newItem.kod
        }

        override fun areContentsTheSame(oldItem: CurrencyRate, newItem: CurrencyRate): Boolean {
            return oldItem == newItem
        }
    }
//    override fun getItemCount(): Int = currencyRates.size
//
//    fun updateData(newCurrencyRates: List<CurrencyRate>) {
//        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
//            override fun getOldListSize(): Int = currencyRates.size
//
//            override fun getNewListSize(): Int = newCurrencyRates.size
//
//            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return currencyRates[oldItemPosition].kod == newCurrencyRates[newItemPosition].kod
//            }
//
//            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//                return currencyRates[oldItemPosition] == newCurrencyRates[newItemPosition]
//            }
//        })
//
//        // Обновляем список и применяем изменения
//        currencyRates.clear()
//        currencyRates.addAll(newCurrencyRates)
//        diffResult.dispatchUpdatesTo(this)
//    }
}
