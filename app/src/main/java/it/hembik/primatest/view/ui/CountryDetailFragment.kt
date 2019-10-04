package it.hembik.primatest.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

class CountryDetailFragment: Fragment() {
    companion object {
        /** Creates project fragment for specific project ID  */
        fun forCountry(countryCode: String): CountryDetailFragment {
            val fragment = CountryDetailFragment()
            val args = Bundle()

            //args.putString(COUNTRY_ID_KEY, countryID)
            fragment.setArguments(args)

            return fragment
        }
    }
}