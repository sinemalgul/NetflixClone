package com.example.netflixclone.ui.comingsoon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.common.Resource
import com.example.netflixclone.data.model.Movie
import com.example.netflixclone.data.repo.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComingSoonViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _moviesState = MutableLiveData(MoviesState())
    val moviesState: LiveData<MoviesState>
        get() = _moviesState

    fun getComingSoonMovies() {
        viewModelScope.launch {
            when (val response = moviesRepository.getComingSoonMovies()) {
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
}

data class MoviesState(
    val moviesList: List<Movie>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)