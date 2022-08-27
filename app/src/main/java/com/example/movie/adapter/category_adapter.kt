package com.example.movie.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.models.category_model

class category_adapter(var items: ArrayList<category_model>) :
    RecyclerView.Adapter<category_adapter.viewholder>() {
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_name = itemView.findViewById<TextView>(R.id.txt_category_name)
        var category_img = itemView.findViewById<ImageView>(R.id.img_category)

        fun append_genre() {


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_category_item, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        var list = items[position]
        holder.category_name.text = list.name
        holder.category_img.setImageResource(list.img)

        holder.itemView.setOnClickListener {
            val b = Bundle()

            val hashMap: HashMap<String, Int> = HashMap()
            hashMap.put("Action", 28)
            hashMap.put("Adventure", 12)
            hashMap.put("Animation", 16)
            hashMap.put("Comedy", 35)
            hashMap.put("Crime", 80)
            hashMap.put("Documentary", 99)
            hashMap.put("Drama", 18)
            hashMap.put("Family", 10751)
            hashMap.put("Fantasy", 14)
            hashMap.put("History", 36)
            hashMap.put("Horror", 27)
            hashMap.put("Music", 10402)
            hashMap.put("Mystery", 9648)
            hashMap.put("Romance", 10749)
            hashMap.put("Science Fiction", 878)
            hashMap.put("TV Movie", 10770)
            hashMap.put("Thriller", 53)
            hashMap.put("War", 10752)
            hashMap.put("Western", 37)



            b.putInt("id", hashMap.getValue(holder.category_name.text.toString()))

            it.findNavController().navigate(R.id.action_nav_home_to_movie_by_category_Fragment, b)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}