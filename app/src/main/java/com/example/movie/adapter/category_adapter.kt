package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R
import com.example.movie.models.category_model

class category_adapter (var items :ArrayList<category_model>) :
    RecyclerView.Adapter<category_adapter.viewholder>() {
    class  viewholder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var category_name = itemView.findViewById<TextView>(R.id.txt_category_name)
        var category_img = itemView.findViewById<ImageView>(R.id.img_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_category_item,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        var list = items[position]
        holder.category_name.text = list.name
        holder.category_img.setImageResource(list.img)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}