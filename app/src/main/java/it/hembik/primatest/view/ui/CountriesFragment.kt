package it.hembik.primatest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.apollographql.apollo.exception.ApolloNetworkException
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.R
import it.hembik.primatest.databinding.FragmentCountriesBinding
import it.hembik.primatest.repository.models.CountriesRepoModel
import it.hembik.primatest.view.adapter.CountriesAdapter
import it.hembik.primatest.view.callback.CountryClickCallback
import it.hembik.primatest.viewmodel.CountriesViewModel

class CountriesFragment: Fragment() {
    companion object {
        val TAG = CountriesFragment::class.qualifiedName
    }

    private lateinit var viewModel: CountriesViewModel
    var countriesAdapter: CountriesAdapter? = null
    var searchView: SearchView? = null
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private lateinit var binding: FragmentCountriesBinding

    private val countryClickCallback = object: CountryClickCallback {
        override fun onClick(country: CountriesQuery.Country) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as MainActivity).showCountryDetail(country)
            }
        }
    }

    private val searchListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            countriesAdapter?.filter?.filter(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            countriesAdapter?.filter?.filter(newText)
            return false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false)
        countriesAdapter = CountriesAdapter(countryClickCallback)

        binding.countriesList.adapter = countriesAdapter
        swipeToRefresh = binding.swipeToRefresh
        swipeToRefresh.setOnRefreshListener {
            viewModel.refreshUsers()
        }

        searchView = binding.searchField
        searchView?.setOnQueryTextListener(searchListener)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // View model is not destroyed on configuration changes, api is done only at creation.
        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        swipeToRefresh.isRefreshing = true
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: CountriesViewModel) {
        // Update the list when the data changes
        viewModel.getCountriesObservable().observe(this,
            Observer<CountriesRepoModel> { countriesModel ->
                swipeToRefresh.isRefreshing = false
                countriesModel.throwable?.let {
                    if (it.cause is ApolloNetworkException) {
                        Toast.makeText(context, getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                    }
                } ?: run {
                    countriesModel.countriesData?.let {
                        binding.dataAvailable = it.countries()?.isNotEmpty() ?: false
                        countriesAdapter?.setCountries(it)
                        searchView?.let { searchView ->
                            // Performs query when configuration changes. State is handled by android lifecycle.
                            searchView.setQuery(searchView.query, true)
                        }
                    }
                }
        })
    }
}