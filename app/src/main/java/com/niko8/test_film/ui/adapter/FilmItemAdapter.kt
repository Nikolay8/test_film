package com.niko8.test_film.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.niko8.test_film.R
import com.niko8.test_film.data.PageResponse
import com.squareup.picasso.Picasso

class FilmItemAdapter(
    private val context: Context,
    private val filmList: MutableList<PageResponse.FilmModel>,
    private val needNewPage: NeedNewPage,
    private val onImageClickCallback: OnImageClickCallback
) : RecyclerView.Adapter<FilmItemAdapter.ViewHolder>() {

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p"
        const val IMAGE_SIZE = "/w500/"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = filmList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = filmList[position]

        holder.titleTextView.text = listItem.title

        val popularityString =
            context.getString(R.string.popularity) + " " + listItem.popularity.toInt()
        holder.popularityTextView.text = popularityString

        val imagePath = IMAGE_BASE_URL + IMAGE_SIZE + listItem.posterPath
        Picasso.get().load(imagePath).into(holder.pictureImageView)

        holder.mainLayout.setOnClickListener {
            onImageClickCallback.onFilmClick(listItem)
        }

        if (position == filmList.size - 1) {
            needNewPage.nextPageRandom()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mainLayout: LinearLayout = view.findViewById(R.id.item_main_layout)
        val pictureImageView: ImageView = view.findViewById(R.id.item_picture_image_view)
        val titleTextView: TextView = view.findViewById(R.id.item_title_text_view)
        val popularityTextView: TextView = view.findViewById(R.id.item_popularity_text_view)
    }

    interface NeedNewPage {
        fun nextPageRandom()
        fun nextPageSearch()
    }

    interface OnImageClickCallback {
        fun onFilmClick(selectedFilm: PageResponse.FilmModel)
    }
}