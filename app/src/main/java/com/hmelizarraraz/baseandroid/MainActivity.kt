package com.hmelizarraraz.baseandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.hmelizarraraz.baseandroid.data.MoviesRepository
import com.hmelizarraraz.baseandroid.data.local.LocalDataSource
import com.hmelizarraraz.baseandroid.data.local.MoviesDatabase
import com.hmelizarraraz.baseandroid.data.remote.RemoteDataSource
import com.hmelizarraraz.baseandroid.ui.screens.home.Home

class MainActivity : ComponentActivity() {

    private lateinit var db : MoviesDatabase

    // En XML vistas clasicas
    // val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = Room.databaseBuilder(
            applicationContext,
            MoviesDatabase::class.java, "movies-db"
        ).build()

        val repository = MoviesRepository(
            localDataSource = LocalDataSource(db.moviesDao()),
            remoteDataSource = RemoteDataSource()
        )

        setContent {
            Home(repository)
        }
    }
}