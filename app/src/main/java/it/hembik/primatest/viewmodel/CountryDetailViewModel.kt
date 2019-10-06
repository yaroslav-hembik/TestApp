package it.hembik.primatest.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.hembik.primatest.CountryDetailQuery
import it.hembik.primatest.repository.CountryRepository
import it.hembik.primatest.repository.models.CountryDetailRepoModel

class CountryDetailViewModel(application: Application, countryCode: String?): AndroidViewModel(application) {
    private val countryObservable: MutableLiveData<CountryDetailRepoModel> = CountryRepository().getCountryDetail(countryCode)
    var country: ObservableField<CountryDetailQuery.Country> = ObservableField()

    fun getCountryDetailObservable(): MutableLiveData<CountryDetailRepoModel> {
        return countryObservable
    }

    /**
     * A factory is used to inject the country code into the ViewModel
     */
    class Factory(private val application: Application, private val countryCode: String?) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CountryDetailViewModel(application, countryCode) as T
        }
    }

    fun setCountryObservable(country: CountryDetailQuery.Country) {
        this.country.set(country)
    }
}