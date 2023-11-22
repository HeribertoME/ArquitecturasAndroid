package com.hmelizarraraz.baseandroid.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmelizarraraz.baseandroid.data.Movie
import com.hmelizarraraz.baseandroid.data.MoviesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MoviesRepository) : ViewModel() {
    // Con state flow
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            repository.requestMovies()

            repository.movies.collect {
                _state.value = UiState(movies = it)
            }
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }

    /*
    var state by mutableStateOf(UiState())  // Se usa para compose
        //private set
    init {
        viewModelScope.launch {
            state = UiState(Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesService::class.java)
                .getMovies()
                .results)
        }
    }
     */

    /*
    // Con live data
    private val _state = MutableLiveData(UiState())
    val state: LiveData<UiState> = _state // Se usa para xml <LiveData>

    init {
        viewModelScope.launch {
            _state.value = UiState(Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesService::class.java)
                .getMovies()
                .results)
        }
    }
     */

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie> = emptyList()
    )

}