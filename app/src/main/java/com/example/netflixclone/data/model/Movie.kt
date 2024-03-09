package com.example.netflixclone.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "moviesDownload")
@Parcelize
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,

    @ColumnInfo(name = "title")
    val title : String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "poster")
    @SerializedName("imageOne")
    val poster: String?,

    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageTwo")
    val imageUrl: String?,

    @ColumnInfo(name = "genres")
    @SerializedName("category")
    val genres: String?,

    @ColumnInfo(name = "trailer")
    @SerializedName("imageThree")
    val trailer: String,

    @ColumnInfo(name = "date")
    @SerializedName("count")
    val date: Int?,

    @ColumnInfo(name = "isComingSoon")
    @SerializedName("saleState")
    val isComingSoon: Boolean?,

    val isDownload: Boolean = false
) : Parcelable
