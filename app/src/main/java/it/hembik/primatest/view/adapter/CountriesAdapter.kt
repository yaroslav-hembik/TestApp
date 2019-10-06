package it.hembik.primatest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.R
import it.hembik.primatest.databinding.CountriesItemBinding
import it.hembik.primatest.utils.CountryFilter
import it.hembik.primatest.view.callback.CountryClickCallback


class CountriesAdapter(private val countryClickCallback: CountryClickCallback): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>(), Filterable {
    private val IMAGE_HOST = "https://www.countryflags.io/"
    private val IMAGE_LOCATION = "/shiny/64.png"
    private var countriesList: CountriesQuery.Data? = null
    private var filteredCountriesList: CountriesQuery.Data? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val binding: CountriesItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.countries_item,
            parent, false)

        binding.callback = countryClickCallback
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredCountriesList?.countries()?.size ?: 0
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val code = filteredCountriesList?.countries()!![position]?.code()
        Glide
            .with(holder.itemView.context)
            .load(IMAGE_HOST + code + IMAGE_LOCATION)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.countryLogo)
        holder.binding.country = filteredCountriesList?.countries()!![position]
        holder.binding.countryName.text = filteredCountriesList?.countries()!![position]?.name()
    }

    fun setCountries(countries: CountriesQuery.Data) {
        countriesList = countries
        filteredCountriesList = countriesList
        notifyDataSetChanged()
    }

    fun setFilteredCountries(countries: CountriesQuery.Data) {
        filteredCountriesList = countries
    }

    override fun getFilter(): Filter {
        return CountryFilter(this, countriesList, filteredCountriesList)
    }

    class CountryViewHolder(val binding: CountriesItemBinding): RecyclerView.ViewHolder(binding.root)
}