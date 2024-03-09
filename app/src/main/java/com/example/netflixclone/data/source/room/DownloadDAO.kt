package com.example.netflixclone.data.source.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.netflixclone.data.model.Movie

@Dao
interface DownloadDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDownload(places: Movie)

    @Query("SELECT * FROM moviesDownload")
    fun getDownload(): List<Movie>?

    @Query("DELETE FROM moviesDownload WHERE id = :idInput")
    fun deleteDownloadWithId(idInput: Int)

    @Query("DELETE FROM moviesDownload")
    fun clearDownload()

    @Query("SELECT id FROM moviesDownload")
    fun getDownloadIds(): List<Int>?
}