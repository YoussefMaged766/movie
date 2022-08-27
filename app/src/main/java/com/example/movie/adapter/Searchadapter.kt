package com.example.movie.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.movie
import com.example.movie.util.constants

class Searchadapter(var items :ArrayList<movie>): RecyclerView.Adapter<Searchadapter.viewholder>() {

    class viewholder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
        var txtdate = itemView.findViewById<TextView>(R.id.txtreleasedate)
        fun bind(data :movie){
            txtTitle.text = data.title
            txtdate.text = data.releaseDate
            Glide.with(itemView).load(constants.img_link+data.posterPath).into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
      var view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
     holder.bind(items[position])

        holder.itemView.setOnClickListener {
            var bundle = Bundle()
            bundle.putSerializable("movie_details", items.get(position))
            it.findNavController().navigate(R.id.action_searchFragment_to_nav_detailed, bundle)
//            it.findNavController().navigate(R.id.action_top_ratedFragment_to_nav_detailed, bundle)


        }
    }

    override fun getItemCount(): Int {
      return items.size
    }

    fun getdata(list :ArrayList<movie>){
        this.items=list
        notifyDataSetChanged()
    }
}