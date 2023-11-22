package com.hmelizarraraz.baseandroid.data.remote

import retrofit2.http.GET

interface MoviesService {

    @GET("discover/movie?api_key=36085a2beed6820970b1a47e9bed8b67")
    suspend fun getMovies(): MovieResult
}