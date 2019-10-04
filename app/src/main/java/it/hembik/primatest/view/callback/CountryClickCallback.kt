package it.hembik.primatest.view.callback

import it.hembik.primatest.CountriesQuery

interface CountryClickCallback {
    fun onClick(country: CountriesQuery.Country)
}