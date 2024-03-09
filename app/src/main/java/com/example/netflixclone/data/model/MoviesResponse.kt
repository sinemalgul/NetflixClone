package com.example.netflixclone.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("products") var movie: List<Movie>?,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
)
