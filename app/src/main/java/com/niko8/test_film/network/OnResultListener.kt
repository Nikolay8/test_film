package com.niko8.test_film.network

import com.niko8.test_film.data.PageResponse

interface OnResultListener {

    fun onSuccess(pageResponse: PageResponse)

    fun onError(message: String?)
}