package com.niko8.test_film.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.niko8.test_film.R
import com.niko8.test_film.data.FilmDetailResponse
import com.niko8.test_film.data.PageResponse
import com.niko8.test_film.network.ApiClient
import com.niko8.test_film.network.OnDetailResultListener
import com.niko8.test_film.ui.adapter.FilmItemAdapter
import com.niko8.test_film.ui.adapter.FilmItemAdapter.Companion.IMAGE_BASE_URL
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var originalTitleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var releaseDateTextView: TextView
    private lateinit var revenueTextView: TextView
    private lateinit var posterImageView: ImageView

    private val apiClient = ApiClient()
    private var filmModel: PageResponse.FilmModel? = null
    private var filmDetailResponse: FilmDetailResponse? = null

    companion object {
        private const val SELECTED_FILM = "selectedFilm"

        fun newInstance(
            filmModel: PageResponse.FilmModel
        ): DetailFragment {
            return DetailFragment().apply {
                arguments = bundleOf(SELECTED_FILM to filmModel)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_film_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmModel = requireArguments().getParcelable<PageResponse.FilmModel>(SELECTED_FILM)

        initViews()
        getFilmDetails()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        filmModel = savedInstanceState?.getParcelable<PageResponse.FilmModel>(SELECTED_FILM)
    }

    private fun initViews() {
        originalTitleTextView = view!!.findViewById(R.id.detail_fragment_original_title_text_view)
        overviewTextView = view!!.findViewById(R.id.detail_fragment_overview_text_view)
        releaseDateTextView = view!!.findViewById(R.id.detail_fragment_release_date_text_view)
        revenueTextView = view!!.findViewById(R.id.detail_fragment_revenue_text_view)
        posterImageView = view!!.findViewById(R.id.detail_fragment_poster_image_view)
    }

    private fun getFilmDetails() {
        filmModel?.id?.let {
            apiClient.getFilmDetail(it, object : OnDetailResultListener {
                override fun onSuccess(filmDetailResponse: FilmDetailResponse) {
                    this@DetailFragment.filmDetailResponse = filmDetailResponse
                    insertData()
                }

                override fun onError(message: String?) {
                    Log.d("TAF", message.toString())
                }

            })
        }
    }

    private fun insertData() {
        originalTitleTextView.text = filmDetailResponse?.title
        overviewTextView.text = filmDetailResponse?.overview

        val releaseDateString =
            getString(R.string.releaseDate) + " " + filmDetailResponse?.releaseDate
        releaseDateTextView.text = releaseDateString

        val revenueString =
            getString(R.string.revenue) + " " + NumberFormat.getCurrencyInstance(Locale("en", "US"))
                .format(filmDetailResponse?.revenue)

        revenueTextView.text = revenueString

        val imagePath = IMAGE_BASE_URL + FilmItemAdapter.IMAGE_SIZE + filmDetailResponse?.posterPath
        Picasso.get().load(imagePath).into(posterImageView)
    }
}