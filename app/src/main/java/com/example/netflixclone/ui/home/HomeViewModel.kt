package com.example.netflixclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.common.Resource
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.data.model.Series
import com.example.netflixclone.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _moviesState = MutableLiveData(MoviesState())
    val moviesState: LiveData<MoviesState>
        get() = _moviesState

    private var _seriesState = MutableLiveData(SeriesState())
    val seriesState: LiveData<SeriesState>
        get() = _seriesState

    fun getMovies () {
        viewModelScope.launch {
            when (val response = moviesRepository.getMovies()) {
                is Resource.Success -> {
                    _moviesState.value = MoviesState(moviesList = response.data)
                }

                is Resource.Fail -> {
                    _moviesState.value = MoviesState(failMessage = response.message)
                }

                is Resource.Error -> {
                    _moviesState.value = MoviesState(errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun getSeries () {
        viewModelScope.launch {
            when (val response = moviesRepository.getSeries()) {
                is Resource.Success -> {
                    _seriesState.value = SeriesState(seriesList = response.data)
                }

                is Resource.Fail -> {
                    _seriesState.value = SeriesState(failMessage = response.message)
                }

                is Resource.Error -> {
                    _seriesState.value = SeriesState(errorMessage = response.throwable.message)
                }
            }
        }
    }
}

data class MoviesState(
    val moviesList: List<Movie>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)

data class SeriesState(
    val seriesList: List<Series>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)