package it.hembik.primatest.repository

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloNetworkException
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.CountryDetailQuery
import it.hembik.primatest.repository.models.CountriesRepoModel
import it.hembik.primatest.repository.models.CountryDetailRepoModel
import okhttp3.OkHttpClient

class CountryRepository {
    private val BASE_URL = "https://countries.trevorblades.com/"
    private var apolloClient: ApolloClient? = null

    private fun apolloClient(): ApolloClient? {
        return apolloClient
    }

    fun getCountries(): MutableLiveData<CountriesRepoModel> {
        val data = MutableLiveData<CountriesRepoModel>()
        setupClient()
        apolloClient()?.query(
            CountriesQuery.builder()
                .build()
        )?.enqueue(object: ApolloCall.Callback<CountriesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                data.postValue(CountriesRepoModel(throwable = Throwable(e)))
            }

            override fun onResponse(response: Response<CountriesQuery.Data>) {
                data.postValue(CountriesRepoModel(countriesData = response.data()))
            }

            override fun onNetworkError(e: ApolloNetworkException) {
                data.postValue(CountriesRepoModel(throwable = Throwable(e)))
            }
        })
        return data
    }

    fun getCountryDetail(code: String?): MutableLiveData<CountryDetailRepoModel> {
        val data = MutableLiveData<CountryDetailRepoModel>()
        setupClient()
        apolloClient()?.query(
            CountryDetailQuery.builder().code(code)
                .build()
        )?.enqueue(object: ApolloCall.Callback<CountryDetailQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                data.postValue(CountryDetailRepoModel(throwable = Throwable(e)))
            }

            override fun onResponse(response: Response<CountryDetailQuery.Data>) {
                data.postValue(CountryDetailRepoModel(countryData = response.data()))
            }

            override fun onNetworkError(e: ApolloNetworkException) {
                data.postValue(CountryDetailRepoModel(throwable = Throwable(e)))
            }
        })
        return data
    }

    private fun setupClient() {
        val okHttpClient = OkHttpClient.Builder().build()

        apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}