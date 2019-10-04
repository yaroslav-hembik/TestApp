package it.hembik.primatest.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import it.hembik.primatest.CountriesQuery
import it.hembik.primatest.R
import it.hembik.primatest.databinding.CountriesItemBinding
import it.hembik.primatest.utils.ImageLoader
import it.hembik.primatest.view.callback.CountryClickCallback
import kotlinx.android.synthetic.main.countries_item.view.*

class CountriesAdapter(private val countryClickCallback: CountryClickCallback): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    var countriesList: CountriesQuery.Data? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val binding: CountriesItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.countries_item,
            parent, false)
        return CountryViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return countriesList?.countries()?.size ?: 0
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        //val bitmap = ImageLoader { bitmap -> holder.itemView.country_logo.setImageBitmap(bitmap) }.execute((countriesList?.countries()!![position]?.code()))

        val code = countriesList?.countries()!![position]?.code()
        Glide
            .with(holder.itemView.context)
            .load("https://www.countryflags.io/$code/shiny/64.png")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.itemView.country_logo)
        holder.itemView.country_code.text = countriesList?.countries()!![position]?.code()
        holder.itemView.country_name.text = countriesList?.countries()!![position]?.name()
    }

    fun setCountries(countries: CountriesQuery.Data) {
        countriesList = countries
        notifyDataSetChanged()
        /*countriesList?.let {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return countriesList?.countries()!![oldItemPosition].code() === countriesList?.countries()!![newItemPosition].code()
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return countriesList?.countries()!![oldItemPosition].code() === countriesList?.countries()!![newItemPosition].code() // TODO IMPROVE
                }

                override fun getOldListSize(): Int {
                    return it.countries()?.size ?: 0
                }

                override fun getNewListSize(): Int {
                    return countries.countries()?.size ?: 0
                }
            })
            countriesList = countries
            result.dispatchUpdatesTo(this)

        } ?: run {
            countriesList = countries
            notifyItemRangeInserted(0, countries.countries()?.size ?: 0)
        }*/
    }

    class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}