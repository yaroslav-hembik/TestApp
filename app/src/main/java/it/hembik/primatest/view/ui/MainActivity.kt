package it.hembik.primatest.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add project list fragment if this is first creation
        savedInstanceState ?: run {
            val fragment = CountriesFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment, CountriesFragment.TAG).commit()
        }
    }

    /**
     * Shows country detail fragment.
     * @param country country instance.
     */
    fun showCountryDetail(country: CountriesQuery.Country) {
        country.code()?.let { code ->
            val countryFragment = CountryDetailFragment.forCountry(code)

            supportFragmentManager
                .beginTransaction()
                .addToBackStack("country")
                .replace(
                    R.id.fragment_container,
                    countryFragment, null
                ).commit()
        }
    }
}
