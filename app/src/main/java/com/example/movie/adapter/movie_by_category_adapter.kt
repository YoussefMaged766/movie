package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.models.movie
import com.example.movie.util.constants

class movie_by_category_adapter(var items: ArrayList<movie>) :
    RecyclerView.Adapter<movie_by_category_adapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.movieImg)
        var txtTitle = itemView.findViewById<TextView>(R.id.txttitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movieitem,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        var list = items[position]
        holder.txtTitle.text = list.title
        Glide.with(holder.itemView).load(constants.img_link + list.posterPath).into(holder.img)
    }

    override fun getItemCount(): Int {
        return items.size
    }
    fun getdata( data :ArrayList<movie>){
        this.items=data
        notifyDataSetChanged()
    }
}