package com.niko8.test_film.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmDetailResponse(
    var adult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @SerializedName("belongs_to_collection")
    var belongsToCollection: BelongsToCollection = BelongsToCollection(),
    var budget: Int = 0,
    var genres: List<Genre> = listOf(),
    var homepage: String = "",
    var id: Int = 0,
    @SerializedName("imdb_id")
    var imdbId: String = "",
    @SerializedName("original_language")
    var originalLanguage: String = "",
    @SerializedName("original_title")
    var originalTitle: String = "",
    var overview: String = "",
    var popularity: Double = 0.0,
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany> = listOf(),
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry> = listOf(),
    @SerializedName("release_date")
    var releaseDate: String = "",
    var revenue: Int = 0,
    var runtime: Int = 0,
    @SerializedName("spoken_languages")
    var spokenLanguages: List<SpokenLanguage> = listOf(),
    var status: String = "",
    var tagline: String = "",
    var title: String = "",
    var video: Boolean = false,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    var voteCount: Int = 0
) : Parcelable {
    @Parcelize
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        var backdropPath: String = "",
        var id: Int = 0,
        var name: String = "",
        @SerializedName("poster_path")
        var posterPath: String = ""
    ) : Parcelable

    @Parcelize
    data class Genre(
        var id: Int = 0,
        var name: String = ""
    ) : Parcelable

    @Parcelize
    data class ProductionCompany(
        var id: Int = 0,
        @SerializedName("logo_path")
        var logoPath: String = "",
        var name: String = "",
        @SerializedName("origin_country")
        var originCountry: String = ""
    ) : Parcelable

    @Parcelize
    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        var iso31661: String = "",
        var name: String = ""
    ) : Parcelable

    @Parcelize
    data class SpokenLanguage(
        @SerializedName("english_name")
        var englishName: String = "",
        @SerializedName("iso_639_1")
        var iso6391: String = "",
        var name: String = ""
    ) : Parcelable
}