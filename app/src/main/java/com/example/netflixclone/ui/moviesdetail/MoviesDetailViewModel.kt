package com.example.netflixclone.ui.moviesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.common.Resource
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.data.repo.MoviesRepository
import com.example.netflixclone.ui.home.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private var _movieState = MutableLiveData(MovieState())
    val movieState: LiveData<MovieState>
        get() = _movieState

    fun getMoviesDetail(id: Int) {
        viewModelScope.launch {
            when (val response = moviesRepository.getMoviesDetail(id)) {
                is Resource.Success -> {
                    _movieState.value = MovieState(response.data)
                }

                is Resource.Fail -> {
                    _movieState.value = MovieState(failMessage = response.message)
                }

                is Resource.Error -> {
                    _movieState.value = MovieState(errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun setDownloadState() {
        val movie = movieState.value?.movie

        if(movie?.isDownload != true) {
            moviesRepository.addDownload(movie!!)
        }

        getMoviesDetail(movie.id ?: 1)
    }
}

data class MovieState(
    val movie: Movie? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)