package com.example.netflixclone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Series(
    val id: Int?,

    val title : String?,

    val description: String?,

    @SerializedName("imageOne")
    val poster: String?,

    @SerializedName("imageTwo")
    val trailer: String,

    @SerializedName("category")
    val episodeName: String?,

    @SerializedName("imageThree")
    val episodeImage: String?,

    @SerializedName("count")
    val date: Int?
): Parcelable