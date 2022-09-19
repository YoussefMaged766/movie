package com.example.movie.util

import com.example.movie.R
import com.example.movie.models.category_model

class constants {

    companion object{
        val api_key = "fc316fcd1ff4ce282d3d955845970b8b"
        val img_link = "https://image.tmdb.org/t/p/w500"
        val img_link_Episode = "https://image.tmdb.org/t/p/original"
        val youtubel_link = "https://www.youtube.com/watch?v="

      var  category_list = arrayListOf(
        category_model("Action", R.drawable.fight_gaming_icon),
        category_model("Adventure", R.drawable.icons_adventure),
        category_model("Comedy", R.drawable.icons_comedy),
        category_model("Crime", R.drawable.icons_crime),
        category_model("Documentary", R.drawable.icons_documentary),
        category_model("Drama", R.drawable.icons_drama),
        category_model("Family", R.drawable.icons_family),
        category_model("Fantasy", R.drawable.icons_fantasy),
        category_model("History", R.drawable.icon_history),
        category_model("Horror", R.drawable.icons_horror),
        category_model("Music", R.drawable.icon_music),
        category_model("Mystery", R.drawable.icon_mystery),
        category_model("Romance", R.drawable.icons_romance),
        category_model("Science Fiction", R.drawable.icon_science_fiction_),
        category_model("Thriller", R.drawable.icons_thriller),
        category_model("War", R.drawable.icons_war),
        category_model("Western", R.drawable.icons_western)

        )

    }

}