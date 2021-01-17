package com.niko8.test_film.network

import com.niko8.test_film.data.FilmDetailResponse
import com.niko8.test_film.data.PageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServices {
    @GET("3/movie/popular")
    fun getPage(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<PageResponse>

    @GET("3/movie/{id}")
    fun getFilmDetail(
        @Path("id") movieId:String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Call<FilmDetailResponse>

    @GET("3/search/movie")
    fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("include_adult") includeAdult: Boolean
    ): Call<PageResponse>
}