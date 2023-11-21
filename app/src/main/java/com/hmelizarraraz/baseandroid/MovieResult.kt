package com.hmelizarraraz.baseandroid

data class MovieResult(
    val page: Int,
    val results: List<Servermovie>,
    val total_pages: Int,
    val total_results: Int
)