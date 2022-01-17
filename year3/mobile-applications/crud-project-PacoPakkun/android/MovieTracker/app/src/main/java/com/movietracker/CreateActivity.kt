package com.movietracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.movietracker.domain.Movie
import com.movietracker.srv.Service

class CreateActivity : AppCompatActivity() {
    lateinit var srv: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        srv = intent.getSerializableExtra("service") as Service
    }

    fun handleBack(view: View) {
        val intent = Intent()
        setResult(0, intent)
        finish()
    }

    fun handleAdd(view: View) {
        val titlu: String = findViewById<EditText>(R.id.titlu).text.toString()
        val an: Int = findViewById<EditText>(R.id.an).text.toString().toInt()
        val regizor: String = findViewById<EditText>(R.id.regizor).text.toString()
        val nrsezoane: Int = findViewById<EditText>(R.id.sezoane).text.toString().toInt()
        val movie: Movie = Movie(titlu, an, regizor, nrsezoane, 0)
        srv.createFilm(movie)

        logd("Created ${movie.title} ${movie.an} ${movie.regizor} ${movie.nr_sezoane} ${movie.nota}")
        Toast.makeText(this, "Added movie ${titlu}", Toast.LENGTH_SHORT).show()

        val intent = Intent()
        intent.putExtra("data", movie)
        setResult(1, intent)
        finish()
    }
}