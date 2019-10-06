package it.hembik.primatest.view.ui

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
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
            hideSoftKeyboard(this)
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(CountryDetailFragment.TAG)
                .replace(R.id.fragment_container, countryFragment, null)
                .commit()
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager!!.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }
}
