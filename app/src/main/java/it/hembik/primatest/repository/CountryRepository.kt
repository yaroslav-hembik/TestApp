package it.hembik.primatest.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import it.hembik.primatest.CountriesQuery
import okhttp3.OkHttpClient

class CountryRepository {
    private val BASE_URL = "https://countries.trevorblades.com/"
    private var apolloClient: ApolloClient? = null

    private fun apolloClient(): ApolloClient? {
        return apolloClient
    }

    fun getCountries(): LiveData<CountriesQuery.Data> {
        val data = MutableLiveData<CountriesQuery.Data>()

        // TODO: REFACTOR
        val okHttpClient = OkHttpClient.Builder().build()

        apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()

        apolloClient()?.query(
            CountriesQuery.builder()
                .build()
        )?.enqueue(object: ApolloCall.Callback<CountriesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d("TEST", "ERROR")
            }

            override fun onResponse(response: Response<CountriesQuery.Data>) {
                data.postValue(response.data())
            }

        })
        return data
    }
}