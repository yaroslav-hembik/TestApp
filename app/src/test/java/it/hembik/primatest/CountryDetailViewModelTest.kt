package it.hembik.primatest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import it.hembik.primatest.repository.models.CountryDetailRepoModel
import it.hembik.primatest.viewmodel.CountryDetailViewModel
import junit.framework.Assert
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CountryDetailViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: CountryDetailViewModel
    private val observer: Observer<CountryDetailRepoModel> = mock()
    val latch = CountDownLatch(1)


    @Test
    fun fetchCountryDetail_NotNull() {
        val application = Mockito.mock(Application::class.java)
        viewModel = CountryDetailViewModel(application, "UA")
        viewModel.getCountryDetailObservable().observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        assertNotNull(viewModel.getCountryDetailObservable().value)
    }

    @Test
    fun setCountryObservableTest() {
        val application = Mockito.mock(Application::class.java)
        viewModel = CountryDetailViewModel(application, "UA")
        viewModel.getCountryDetailObservable().observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        assertNotNull(viewModel.getCountryDetailObservable())
        assertNotNull(viewModel.getCountryDetailObservable().value)
        assertNotNull(viewModel.getCountryDetailObservable().value!!.countryData)
        assertNotNull(viewModel.getCountryDetailObservable().value!!.countryData!!.country)
        viewModel.setCountryObservable(viewModel.getCountryDetailObservable().value!!.countryData!!.country!!)
        assertNotNull(viewModel.country)
    }
}