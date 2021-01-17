package com.niko8.test_film.network

import com.niko8.test_film.constant.Const
import com.niko8.test_film.data.FilmDetailResponse
import com.niko8.test_film.data.PageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiClient {

    private var mService = Common.retrofitService
    private val languageConfig = "en-US"

    fun getDataPage(page: Int, onResultListener: OnResultListener) {
        mService.getPage(Const().ACCESS_KEY.toString(), languageConfig, page)
            .enqueue(object : Callback<PageResponse> {
                override fun onResponse(
                    call: Call<PageResponse>,
                    response: Response<PageResponse>
                ) {
                    try {
                        onResultListener.onSuccess(response.body() as PageResponse)
                    } catch (exception: Exception) {
                        onResultListener.onError(exception.message)
                    }
                }

                override fun onFailure(call: Call<PageResponse>, t: Throwable) {
                    onResultListener.onError(t.message)
                }
            })
    }

    fun getFilmDetail(filmId: Int, onDetailResultListener: OnDetailResultListener) {
        mService.getFilmDetail(
            filmId.toString(),
            Const().ACCESS_KEY.toString(), languageConfig
        )
            .enqueue(object : Callback<FilmDetailResponse> {
                override fun onResponse(
                    call: Call<FilmDetailResponse>,
                    response: Response<FilmDetailResponse>
                ) {
                    try {
                        onDetailResultListener.onSuccess(response.body() as FilmDetailResponse)
                    } catch (exception: Exception) {
                        onDetailResultListener.onError(exception.message)
                    }
                }

                override fun onFailure(call: Call<FilmDetailResponse>, t: Throwable) {
                    onDetailResultListener.onError(t.message)
                }

            })

    }

    fun searchFilm(
        query: String,
        page: Int,
        includeAdult: Boolean,
        onResultListener: OnResultListener
    ) {
        mService.searchMovie(
            Const().ACCESS_KEY.toString(),
            languageConfig,
            query,
            page,
            includeAdult
        )
            .enqueue(object : Callback<PageResponse> {
                override fun onResponse(
                    call: Call<PageResponse>,
                    response: Response<PageResponse>
                ) {
                    try {
                        onResultListener.onSuccess(response.body() as PageResponse)
                    } catch (exception: Exception) {
                        onResultListener.onError(exception.message)
                    }
                }

                override fun onFailure(call: Call<PageResponse>, t: Throwable) {
                    onResultListener.onError(t.message)
                }

            })
    }

}