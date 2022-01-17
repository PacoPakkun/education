package com.movietracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import com.movietracker.domain.Movie
import com.movietracker.srv.Service
import android.content.DialogInterface
import android.widget.*


class EditActivity : AppCompatActivity() {
    lateinit var srv: Service
    lateinit var movie: Movie

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        srv = intent.getSerializableExtra("service") as Service
        movie = intent.getSerializableExtra("movie") as Movie

        findViewById<TextView>(R.id.titlu).text = movie.title
        findViewById<TextView>(R.id.info).text =
            "${movie.an} | ${movie.regizor} | ${movie.nr_sezoane} seasons | ${movie.nota} stars"
    }

    fun handleBack(view: View) {
        val intent = Intent()
        intent.putExtra("data", movie)
        setResult(2, intent)
        finish()
    }

    fun handleDelete(view: View) {
        AlertDialog.Builder(this)
            .setTitle("Delete movie")
            .setMessage("Are you sure you want to delete this movie?")
            .setPositiveButton(
                android.R.string.yes
            ) { dialog, which ->
                srv.deleteFilm(movie)
                logd("Deleted ${movie.title} ${movie.an} ${movie.regizor} ${movie.nr_sezoane} ${movie.nota}")
                Toast.makeText(this, "Deleted movie ${movie.title}", Toast.LENGTH_SHORT).show()

                val intent = Intent()
                intent.putExtra("data", movie)
                setResult(3, intent)
                finish()
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun handleEdit(view: View) {
        val an = findViewById<TextView>(R.id.an2)
        an.visibility = View.VISIBLE
        an.text = movie.an.toString()
        val regizor = findViewById<TextView>(R.id.regizor2)
        regizor.visibility = View.VISIBLE;
        regizor.text = movie.regizor
        val sezoane = findViewById<TextView>(R.id.sezoane2)
        sezoane.visibility = View.VISIBLE;
        sezoane.text = movie.nr_sezoane.toString()
        findViewById<TextView>(R.id.button).visibility = View.VISIBLE;
        findViewById<TextView>(R.id.info).visibility = View.GONE;
    }

    @SuppressLint("CutPasteId", "SetTextI18n")
    fun handleUpdate(view: View) {
        val an: Int = findViewById<EditText>(R.id.an2).text.toString().toInt()
        val regizor: String = findViewById<EditText>(R.id.regizor2).text.toString()
        val nrsezoane: Int = findViewById<EditText>(R.id.sezoane2).text.toString().toInt()
        val newMovie: Movie = Movie(movie.title, an, regizor, nrsezoane, movie.nota)
        srv.updateFilm(newMovie)
        movie = newMovie
        findViewById<TextView>(R.id.info).text =
            "${movie.an} | ${movie.regizor} | ${movie.nr_sezoane} seasons | ${movie.nota} stars"

        logd("Updated ${movie.title} ${movie.an} ${movie.regizor} ${movie.nr_sezoane} ${movie.nota}")
        Toast.makeText(this, "Updated movie ${movie.title}", Toast.LENGTH_SHORT).show()

        findViewById<TextView>(R.id.an2).visibility = View.GONE;
        findViewById<TextView>(R.id.regizor2).visibility = View.GONE;
        findViewById<TextView>(R.id.sezoane2).visibility = View.GONE;
        findViewById<TextView>(R.id.button).visibility = View.GONE;
        findViewById<TextView>(R.id.info).visibility = View.VISIBLE;
    }
}