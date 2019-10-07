package it.hembik.primatest.utils

import android.widget.Filter
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.view.adapter.CountriesAdapter
import java.util.*
import kotlin.collections.ArrayList

open class CountryFilter(private val adapter: CountriesAdapter, private val countries: CountriesQuery.Data?, private var filteredCountries: CountriesQuery.Data?): Filter() {
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

    /**
     * Filters countries.
     * @param sequenceString searched string.
     */
     fun filterCountries(sequenceString: String) {
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

    /**
     * Checks for continent match.
     * @param continent current continent.
     * @param sequence searched sequence.
     * @return true if match, false otherwise.
     */
    fun continentMatch(continent: String?, sequence: String?): Boolean {
        continent?.let {
            sequence?.let {
                return stringMatch(continent, sequence)
            }
        }
        return false
    }

    /**
     * Checks for language match.
     * @param language current language.
     * @param sequence searched sequence.
     * @return true if match, false otherwise.
     */
    fun languageMatch(languageList: List<CountriesQuery.Language>, sequence: String): Boolean {
        for (language in languageList) {
            language.name()?.let { languageName ->
                sequence.let {
                    return stringMatch(languageName, sequence)
                }
            }
        }
        return false
    }

    /**
     * Checks for string containing sequence.
     * @param first first string.
     * @param second second string.
     * @return true if first contains second, false otherwise.
     */
    fun stringMatch(first: String, second: String): Boolean {
        return first.toLowerCase(Locale.getDefault()).contains(second.toLowerCase(Locale.getDefault()))

    }
}