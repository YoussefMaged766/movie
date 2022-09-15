package com.example.movie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.movie.R
import com.example.movie.models.SlidingDataIntro

class SlidingAdapter(var items: List<SlidingDataIntro>) :
    RecyclerView.Adapter<SlidingAdapter.viewholder>() {

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<LottieAnimationView>(R.id.lottieImg)
        val txt = itemView.findViewById<TextView>(R.id.txtDis)
        fun bing(data: SlidingDataIntro) {
            img.setAnimation(data.img)
            txt.text = data.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sliding_intro, parent, false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bing(items[position])
    }

    override fun getItemCount(): Int {

        return items.size
    }
}