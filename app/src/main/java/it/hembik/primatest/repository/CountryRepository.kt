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

    /**
     * Apollo client getter. Makes setup if null.
     */
    private fun getApolloClient(): ApolloClient {
        return apolloClient?.let {
            it
        } ?: run {
            setupClient()
        }
    }

    /**
     * Gets list of countries.
     */
    fun getCountries(): MutableLiveData<CountriesRepoModel> {
        val data = MutableLiveData<CountriesRepoModel>()
        getApolloClient().query(
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

    /**
     * Gets country detail.
     * @param code country code.
     */
    fun getCountryDetail(code: String?): MutableLiveData<CountryDetailRepoModel> {
        val data = MutableLiveData<CountryDetailRepoModel>()
        getApolloClient().query(
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

    /**
     * Setups apollo client
     */
    private fun setupClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().build()

        return ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}