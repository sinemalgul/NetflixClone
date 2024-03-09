package com.example.netflixclone.data.model

import com.google.gson.annotations.SerializedName

data class SeriesResponse(
    @SerializedName("products") var series: List<Series>?,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
)