package com.movietracker.domain

import java.io.Serializable

class Movie(
    var title: String,
    var an: Int,
    var regizor: String,
    var nr_sezoane: Int,
    var nota: Int?
) : Serializable {
    override fun toString(): String {
        return "${title} ${an}"
    }
}