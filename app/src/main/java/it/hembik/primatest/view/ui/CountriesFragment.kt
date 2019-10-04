package it.hembik.primatest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.R
import it.hembik.primatest.databinding.FragmentCountriesBinding
import it.hembik.primatest.view.adapter.CountriesAdapter
import it.hembik.primatest.view.callback.CountryClickCallback
import it.hembik.primatest.viewmodel.CountriesViewModel

class CountriesFragment: Fragment() {
    companion object {
        val TAG = "CountriesFragment"
    }

    var countriesAdapter: CountriesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCountriesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)
        countriesAdapter = CountriesAdapter(countryClickCallback)
        binding.countriesList.adapter = countriesAdapter
        binding.countriesList.setHasFixedSize(true);
        binding.countriesList.setItemViewCacheSize(20);
        binding.countriesList.setDrawingCacheEnabled(true);
        binding.countriesList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val viewModel: CountriesViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(it.application).create(CountriesViewModel::class.java)
            observeViewModel(viewModel)
        }

    }

    private fun observeViewModel(viewModel: CountriesViewModel) {
        // Update the list when the data changes
        viewModel.getCountriesObservable().observe(this,
            Observer<CountriesQuery.Data> { countries ->
                countries.let {
                    countriesAdapter?.setCountries(it)
                }
            })
    }

    private val countryClickCallback = object : CountryClickCallback {
        override fun onClick(country: CountriesQuery.Country) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).showCountryDetail(country)
            }
        }
    }

}