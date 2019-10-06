package it.hembik.primatest.view.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import it.hembik.primatest.CountryDetailQuery

const val PLACEHOLDER = "-"

@BindingAdapter("visibleGone")
fun showHide(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("languages")
fun languages(view: TextView, languages: List<CountryDetailQuery.Language>?) {
    view.text = languages?.let { languagesList ->
        if (languagesList.isNotEmpty()) {
            languagesList.joinToString(separator = ", ") { "${it.name()}"}
        } else {
            PLACEHOLDER
        }
    } ?: run {
        PLACEHOLDER
    }
}

@BindingAdapter("currency")
fun currency(view: TextView, currency: String?) {
    view.text = currency?.let {
        if (currency.isNotEmpty()) {
            currency
        } else {
            PLACEHOLDER
        }
    } ?: run {
        PLACEHOLDER
    }
}