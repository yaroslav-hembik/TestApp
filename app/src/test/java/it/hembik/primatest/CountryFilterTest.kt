package it.hembik.primatest

import it.hembik.primatest.utils.CountryFilter
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CountryFilterTest {
    private lateinit var filter: CountryFilter

    @Before
    fun before() {
        filter = Mockito.mock(CountryFilter::class.java)
    }
    @Test
    fun testStringMatch() {
        assertTrue(filter.stringMatch("abcd", "abcd"))
    }

    @Test
    fun testContinentMatch() {
        assertTrue(filter.continentMatch("abcd", "abcd"))
    }

    @Test
    fun testStringNotMatch() {
        assertFalse(filter.stringMatch("abcd", "abcde"))
    }

    @Test
    fun testContinentNotMatch() {
        assertFalse(filter.continentMatch("abcd", "abcde"))
    }

    @Test
    fun testLanguageMatch() {
        val filter = Mockito.mock(CountryFilter::class.java)
        val language2 = CountriesQuery.Language("", "ukrainian")

        assertTrue(filter.languageMatch(listOf(language2), "ukra"))
    }

    @Test
    fun testLanguageNotMatch() {
        val filter = Mockito.mock(CountryFilter::class.java)
        val language2 = CountriesQuery.Language("", "ukrainian")

        assertFalse(filter.languageMatch(listOf(language2), "italian"))
    }

}