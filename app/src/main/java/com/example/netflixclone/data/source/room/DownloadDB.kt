package com.example.netflixclone.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.netflixclone.data.model.Movie

@Database(entities = [Movie::class], version = 2)
abstract class DownloadDB : RoomDatabase() {
    abstract fun downloadDAO() : DownloadDAO
}