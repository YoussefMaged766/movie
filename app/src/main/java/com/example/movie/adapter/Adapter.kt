package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.util.constants
import com.example.movie.models.movie
import java.util.*
import kotlin.collections.ArrayList


class adapter(var list: ArrayList<movie>?=null) : RecyclerView.Adapter<adapter.viewholder>(), Filterable {
    private val searchList = ArrayList<movie>(list)

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
if (list!=null){
    val item = list?.get(position)
    holder.txtTitle.setText(item?.title)
    holder.txtdate.setText(item?.releaseDate)
    holder.setIsRecyclable(false)
    holder.img.clipToOutline = true

    Glide.with(holder.itemView).load(constants.img_link + item?.posterPath).into(holder.img)

    holder.itemView.setOnClickListener {
        var bundle = Bundle()
        bundle.putSerializable("movie_details", item)
        it.findNavController().navigate(R.id.action_top_ratedFragment_to_nav_detailed, bundle)
//            it.findNavController().navigate(R.id.action_top_ratedFragment_to_nav_detailed, bundle)


    }
}




    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    fun getdata(data1: ArrayList<movie>) {
        list = data1
        notifyDataSetChanged()

    }
    fun addlist(list1 :ArrayList<movie>){
        list?.addAll(list1)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filteredList = ArrayList<movie>()

                if (constraint.isBlank() or constraint.isEmpty()) {
                    filteredList.addAll(searchList)
                } else {
//                    val filterPattern = constraint.toString().lowercase(Locale.ROOT)

                    searchList.forEach {
                        if ( it.title?.lowercase(Locale.getDefault())?.contains(constraint.toString().lowercase(Locale.getDefault())) == true) {
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
                list?.addAll(results!!.values as List<movie>)
                notifyDataSetChanged()
            }

        }
    }


}