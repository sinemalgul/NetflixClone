package com.example.netflixclone.data.model

import com.google.gson.annotations.SerializedName

data class SeriesDetailResponse(
    @SerializedName("product") var seriesDetail: Series,
    @SerializedName("status") var status: Int?,
    @SerializedName("message") var message: String?
) {
}