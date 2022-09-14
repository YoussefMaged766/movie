package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie.R
import com.example.movie.TVSeasonsDetailsFragment
import com.example.movie.models.CrewItem
import com.example.movie.models.EpisodesItem
import com.example.movie.models.TVSeasonsDetailsResponse
import com.example.movie.util.constants

class CrewAdapter(var items: List<CrewItem?>) : RecyclerView.Adapter<CrewAdapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.crewProfile)
        val name =itemView.findViewById<TextView>(R.id.txtName)
        fun bind(data: CrewItem) {
            name.text=data.name
            if (data.profilePath==null){
                img.setImageResource(R.drawable.person_account_icon)

            } else{
                Glide.with(itemView).load(constants.img_link + data.profilePath).into(img)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.crew_seasonitem, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(items[position]!!)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}