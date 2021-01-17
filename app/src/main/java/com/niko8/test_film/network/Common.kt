package com.niko8.test_film.network

object Common {
    private const val BASE_URL = "https://api.themoviedb.org"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}