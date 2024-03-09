package com.example.netflixclone.di

import android.content.Context
import androidx.room.Room
import com.example.netflixclone.data.source.room.DownloadDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDBModule {

    @Provides
    @Singleton
    fun provideRoomDB(
        @ApplicationContext context: Context
    ) : DownloadDB = Room.databaseBuilder(
        context,
        DownloadDB::class.java,
        "netflixdownloaddatabase",
    ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideNetflixDownloadDao(
        downloadDB: DownloadDB
    ) = downloadDB.downloadDAO()
}