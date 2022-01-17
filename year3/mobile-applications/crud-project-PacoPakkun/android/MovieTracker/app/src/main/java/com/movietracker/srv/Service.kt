package com.movietracker.srv

import com.movietracker.domain.Movie
import com.movietracker.logd
import com.movietracker.repo.Repository
import java.io.Serializable

class Service() : Serializable {
    var repo: Repository = Repository()

    fun createFilm(film: Movie) {
        repo.createFilm(film)
    }

    fun readFilme(): MutableList<Movie> {
        return repo.readFilme()
    }

    fun updateFilm(film: Movie) {
        repo.updateFilm(film)
    }

    fun deleteFilm(film: Movie) {
        repo.deleteFilm(film)
    }
}