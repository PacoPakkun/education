package com.movietracker.repo

import com.movietracker.domain.Movie
import java.io.Serializable

class Repository() : Serializable {
    val filme: MutableList<Movie> = mutableListOf(
        Movie("Sons of Anarchy", 2015, "Tarantino", 5, 10),
        Movie("Peaky Blinders", 2021, "Scorsese", 4, 9),
        Movie("Vikings", 2016, "Nolan", 2, 8),
        Movie("Mr Robot", 2018, "Cameron", 3, 10),
        Movie("Game of Thrones", 2020, "Kubrick", 7, 7),
    )

    fun createFilm(film: Movie) {
        filme.add(film)
    }

    fun readFilme(): MutableList<Movie> {
        return filme
    }

    fun updateFilm(film: Movie) {
        deleteFilm(film)
        filme.add(film)
    }

    fun deleteFilm(film: Movie) {
        filme.removeIf { f: Movie -> film.title == f.title }
    }
}