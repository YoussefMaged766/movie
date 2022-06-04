package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.ResultsItem1
import com.example.movie.models.ResultsItem_trend
import com.example.movie.models.ResultsItem_trendTV
import com.example.movie.util.constants
import com.example.movie.models.movie
import java.util.*
import kotlin.collections.ArrayList


class adapter_trend_tv(var list: ArrayList<ResultsItem_trendTV>?) :
    RecyclerView.Adapter<adapter_trend_tv.viewholder>(), Filterable {
    private val searchList = ArrayList<ResultsItem_trendTV>(list)

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {

        var item = list?.get(position)
        holder.txtTitle.setText(item?.name)
        holder.txtdate.setText(item?.firstAirDate)
        holder.setIsRecyclable(false)
        holder.img.clipToOutline = true

        Glide.with(holder.itemView).load(constants.img_link + item?.posterPath).into(holder.img)

        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("tv_details", item)
            it.findNavController().navigate(R.id.trend_tvFragment, bundle)

        }


    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun getdata(data1: ArrayList<ResultsItem_trendTV>) {
        list = data1
        notifyDataSetChanged()

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = ArrayList<ResultsItem_trendTV>()

                if (constraint.isBlank() or constraint.isEmpty()) {
                    filteredList.addAll(searchList)
                } else {
//                    val filterPattern = constraint.toString().lowercase(Locale.ROOT)

                    searchList.forEach {
                        if (it.name?.lowercase(Locale.getDefault())?.contains(
                                constraint.toString().lowercase(Locale.getDefault())
                            ) == true
                        ) {
                            filteredList.add(it)


                        }
                    }
                }
                val result = FilterResults()
                result.values = filteredList
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list?.clear()
                list?.addAll(results!!.values as List<ResultsItem_trendTV>)
                notifyDataSetChanged()
            }

        }
    }


}