package com.hmelizarraraz.baseandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

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

    // Con state flow
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            _state.value = UiState(loading = true)
            delay(2000L) // Simulate network()
            _state.value = UiState(
                loading = false,
                movies = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MoviesService::class.java)
                .getMovies()
                .results)
        }
    }

    fun onMovieClick(movie: Servermovie) {
        val movies = _state.value.movies.toMutableList()
        movies.replaceAll { if (it.id == movie.id) movie.copy(favorite = !movie.favorite) else it }
        _state.value = _state.value.copy(movies = movies)
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Servermovie> = emptyList()
    )

}