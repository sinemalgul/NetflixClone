package com.example.netflixclone.data.repo

import com.example.netflixclone.common.Resource
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.data.model.Series
import com.example.netflixclone.data.source.retrofit.MovieService
import com.example.netflixclone.data.source.room.DownloadDAO

class MoviesRepository (
    private val movieService: MovieService,
    private val downloadDAO: DownloadDAO
) {

    suspend fun getMovies() : Resource<List<Movie>> {
        return try {
            val response = movieService.getMovies().body()

            if (response?.status == 200) {
                Resource.Success(response.movie.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getComingSoonMovies() : Resource<List<Movie>> {
        return try {
            val response = movieService.getComingSoonMovies().body()

            if (response?.status == 200) {
                Resource.Success(response.movie.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e:Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getMoviesDetail(id: Int) : Resource<Movie> {
        return try {
            val response = movieService.getMoviesDetail(id).body()

            if (response?.status == 200) {
                val downloadIds = downloadDAO.getDownloadIds().orEmpty()

                Resource.Success(
                    Movie(
                        id = response.movieDetail.id,
                        title = response.movieDetail.title,
                        description = response.movieDetail.description,
                        poster = response.movieDetail.poster,
                        imageUrl = response.movieDetail.imageUrl,
                        genres = response.movieDetail.genres,
                        trailer = response.movieDetail.trailer,
                        date = response.movieDetail.date,
                        isComingSoon = response.movieDetail.isComingSoon,
                        isDownload = downloadIds.contains(response.movieDetail.id ?: 0)
                    )
                )
            } else {
                Resource.Fail(response?.message)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSeries() : Resource<List<Series>> {
        return try {
            val response = movieService.getSeries().body()

            if (response?.status == 200) {
                Resource.Success(response.series.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSeriesDetail(id: Int) : Resource<Series> {
        return try {
            val response = movieService.getSeriesDetail(id).body()

            if (response?.status == 200) {
                Resource.Success(
                    Series(
                        id = response.seriesDetail.id,
                        title = response.seriesDetail.title,
                        description = response.seriesDetail.description,
                        poster = response.seriesDetail.poster,
                        trailer = response.seriesDetail.trailer,
                        episodeName = response.seriesDetail.episodeName,
                        episodeImage = response.seriesDetail.episodeImage,
                        date = response.seriesDetail.date,
                    )
                )
            } else {
                Resource.Fail(response?.message)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun searchProducts(query: String): Resource<List<Movie>> {
        return try {
            val response = movieService.searchProduct(query).body()

            if (response?.status == 200) {
                Resource.Success(response.movie.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun getDownload() : Resource<List<Movie>> {
        return try {
            val response = downloadDAO.getDownload()
            if (response.isNullOrEmpty()) {
                Resource.Fail("Fail")
            } else {
                Resource.Success(response)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun addDownload(movie: Movie) {
        val movieDownload = Movie(
            id = movie.id,
            title = movie.title,
            description = movie.description,
            poster = movie.poster,
            imageUrl = movie.imageUrl,
            genres = movie.genres,
            trailer = movie.trailer,
            date = movie.date,
            isComingSoon = movie.isComingSoon
        )

        downloadDAO.addDownload(movieDownload)
    }

    fun deleteDownload(movieId: Int) = downloadDAO.deleteDownloadWithId(movieId)
}