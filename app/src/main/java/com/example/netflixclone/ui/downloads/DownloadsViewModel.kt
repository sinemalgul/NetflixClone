package com.example.netflixclone.ui.downloads

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
class DownloadsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _downloadsState = MutableLiveData(DownloadsState())
    val downloadsState : LiveData<DownloadsState>
        get() = _downloadsState

    fun getDownloads() {
        viewModelScope.launch {
            when (val response = moviesRepository.getDownload()) {
                is Resource.Success -> {
                    _downloadsState.value = DownloadsState(downloadsList = response.data)
                }

                is Resource.Fail -> {
                    _downloadsState.value = DownloadsState(failMessage = response.message)
                }

                is Resource.Error -> {
                    _downloadsState.value = DownloadsState(errorMessage = response.throwable.message)
                }
            }
        }
    }

    fun deleteDownloads(moviesId: Int) {
        moviesRepository.deleteDownload(moviesId)
        getDownloads()
    }
}

data class DownloadsState(
    val downloadsList: List<Movie>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null
)