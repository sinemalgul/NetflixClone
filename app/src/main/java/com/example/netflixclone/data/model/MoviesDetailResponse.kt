package com.example.netflixclone.data.model

import com.google.gson.annotations.SerializedName

data class MoviesDetailResponse(
    @SerializedName("product") var movieDetail: Movie,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
)
