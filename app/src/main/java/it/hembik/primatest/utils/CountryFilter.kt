package it.hembik.primatest.utils

import android.widget.Filter
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.view.adapter.CountriesAdapter
import java.util.*
import kotlin.collections.ArrayList

class CountryFilter(private val adapter: CountriesAdapter, private val countries: CountriesQuery.Data?, private var filteredCountries: CountriesQuery.Data?): Filter() {
    override fun performFiltering(sequence: CharSequence): FilterResults {
        val sequenceString = sequence.toString()
        if (sequenceString.isEmpty()) {
            filteredCountries = countries
        } else {
            filterCountries(sequenceString)
        }
        val results = FilterResults()
        results.values = filteredCountries
        return results
    }

    override fun publishResults(sequence: CharSequence?, results: FilterResults?) {
        results?.values?.let { searchResults ->
            filteredCountries = searchResults as CountriesQuery.Data
            filteredCountries?.let {
                adapter.setFilteredCountries(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun filterCountries(sequenceString: String) {
        val filteredList = CountriesQuery.Data(ArrayList<CountriesQuery.Country>())

        countries?.countries()?.let { countries ->
            for (country in countries) {
                if (continentMatch(country.continent()?.name(), sequenceString) || languageMatch(country.languages() as List<CountriesQuery.Language>, sequenceString)) {
                    filteredList.countries()?.add(country)
                }
                filteredCountries = filteredList
            }
        }
    }

    private fun continentMatch(continent: String?, sequence: String?): Boolean {
        continent?.let {
            sequence?.let {
                return stringMatch(continent, sequence)
            }
        }
        return false
    }

    private fun languageMatch(languageList: List<CountriesQuery.Language>, sequence: String): Boolean {
        for (language in languageList) {
            language.name()?.let { languageName ->
                sequence.let {
                    return stringMatch(languageName, sequence)
                }
            }
        }
        return false
    }

    private fun stringMatch(first: String, second: String): Boolean {
        return first.toLowerCase(Locale.getDefault()).contains(second.toLowerCase(Locale.getDefault()))

    }
}