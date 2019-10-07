package it.hembik.primatest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import it.hembik.primatest.repository.CountryRepository
import it.hembik.primatest.repository.models.CountriesRepoModel

class CountriesViewModel(application: Application): AndroidViewModel(application) {
    private val reloadTrigger = MutableLiveData<Boolean>()
    private val countriesObservable: LiveData<CountriesRepoModel> = Transformations.switchMap(reloadTrigger) {
        CountryRepository().getCountries()
    }

    init {
        refreshCountries()
    }

    fun getCountriesObservable(): LiveData<CountriesRepoModel> {
        return countriesObservable
    }

    fun refreshCountries() {
        reloadTrigger.value = true
    }
}