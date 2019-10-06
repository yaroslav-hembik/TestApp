package it.hembik.primatest.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apollographql.apollo.exception.ApolloNetworkException
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.hembik.primatest.R
import it.hembik.primatest.databinding.FragmentCountryDetailBinding
import it.hembik.primatest.repository.models.CountryDetailRepoModel
import it.hembik.primatest.viewmodel.CountryDetailViewModel

class CountryDetailFragment: Fragment() {
    private val IMAGE_HOST = "https://www.countryflags.io/"
    private val IMAGE_LOCATION = "/shiny/64.png"
    private lateinit var binding: FragmentCountryDetailBinding
    companion object {
        val TAG = CountryDetailFragment::class.qualifiedName
        val COUNTRY_CODE_KEY = "COUNTRY_CODE_KEY"

        /** Creates project fragment for specific project ID  */
        fun forCountry(countryCode: String): CountryDetailFragment {
            val fragment = CountryDetailFragment()
            val args = Bundle()

            args.putString(COUNTRY_CODE_KEY, countryCode)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_country_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { activity ->
            arguments?.let { args ->
                val factory = CountryDetailViewModel.Factory(activity.application, args.getString(COUNTRY_CODE_KEY))
                val viewModel = ViewModelProviders.of(this, factory).get(CountryDetailViewModel::class.java)

                Glide
                    .with(this)
                    .load(IMAGE_HOST + args.getString(COUNTRY_CODE_KEY) + IMAGE_LOCATION)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.countryLogo)
                binding.countryViewModel = viewModel
                binding.isLoading = true
                observeViewModel(viewModel)
            }

        }
    }

    private fun observeViewModel(viewModel: CountryDetailViewModel) {
        // Update the list when the data changes
        viewModel.getCountryDetailObservable().observe(this,
            Observer<CountryDetailRepoModel> { countryModel ->
                countryModel.throwable?.let {
                    if (it.cause is ApolloNetworkException) {
                        Toast.makeText(context, getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                    }
                } ?: run {
                    countryModel.countryData?.country()?.let {
                        viewModel.setCountryObservable(it)
                    }
                }
                binding.isLoading = false
            })
    }
}