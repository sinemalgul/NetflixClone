package com.example.netflixclone.ui.search

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
class SearchViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private var _searchResult = MutableLiveData<SearchState>()
    val searchResults: LiveData<SearchState>
        get() = _searchResult

    fun searchProducts(query: String) {
        viewModelScope.launch {
            when (val response = moviesRepository.searchProducts(query)) {
                is Resource.Success -> {
                    _searchResult.value = SearchState(
                        searchList = response.data
                    )
                }

                is Resource.Fail -> {
                    _searchResult.value = SearchState(
                        failMessage = response.message
                    )
                }

                is Resource.Error -> {
                    _searchResult.value = SearchState(
                        errorMessage = response.throwable.message
                    )
                }
            }
        }
    }
}

data class SearchState(
    val searchList: List<Movie>? = null,
    val errorMessage: String? = null,
    val failMessage: String? = null
)