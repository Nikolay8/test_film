package com.niko8.test_film.network

import com.niko8.test_film.data.FilmDetailResponse

interface OnDetailResultListener {

    fun onSuccess(filmDetailResponse: FilmDetailResponse)

    fun onError(message: String?)
}