package it.hembik.primatest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.repository.CountryRepository

class CountriesViewModel(application: Application): AndroidViewModel(application) {
    private val countriesObservable: LiveData<CountriesQuery.Data> = CountryRepository().getCountries()

    fun getCountriesObservable(): LiveData<CountriesQuery.Data> {
        return countriesObservable
    }
}