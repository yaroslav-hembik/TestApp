package it.hembik.primatest

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import it.hembik.primatest.repository.models.CountriesRepoModel
import it.hembik.primatest.viewmodel.CountriesViewModel
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class CountriesViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var viewModel: CountriesViewModel
    private val observer: Observer<CountriesRepoModel> = mock()
    val latch = CountDownLatch(1)


    @Test
    fun fetchCountries_NotNull() {
        val application = Mockito.mock(Application::class.java)
        viewModel = CountriesViewModel(application)
        viewModel.getCountriesObservable().observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)
        assertNotNull(viewModel.getCountriesObservable().value)
    }
}