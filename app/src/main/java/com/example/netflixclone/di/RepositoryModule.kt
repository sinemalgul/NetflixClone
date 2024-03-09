package com.example.netflixclone.di

import com.example.netflixclone.data.repo.MoviesRepository
import com.example.netflixclone.data.source.retrofit.MovieService
import com.example.netflixclone.data.source.room.DownloadDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository (
        movieService: MovieService,
        downloadDAO: DownloadDAO
    ) = MoviesRepository(movieService, downloadDAO)
}