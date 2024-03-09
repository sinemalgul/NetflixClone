package com.example.netflixclone.ui.seriesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.netflixclone.common.Resource
import com.example.netflixclone.data.model.Series
import com.example.netflixclone.data.repo.MoviesRepository
import com.example.netflixclone.ui.moviesdetail.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel(){

    private var _seriesState = MutableLiveData(SeriesState())
    val seriesState: LiveData<SeriesState>
        get() = _seriesState

    fun getSeriesDetail(id : Int) {
        viewModelScope.launch {
            when (val response = moviesRepository.getSeriesDetail(id)) {
                is Resource.Success -> {
                    _seriesState.value = SeriesState(response.data)
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

data class SeriesState(
    val series: Series? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null,
)