package com.movietracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.movietracker.domain.Movie
import com.movietracker.srv.Service

class MainActivity : AppCompatActivity() {
    private lateinit var srv: Service
    lateinit var data: MutableList<Movie>

    lateinit var adapter: ArrayAdapter<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logd("On create called")
        setContentView(R.layout.activity_main)

        srv = Service()
        data = srv.readFilme()

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        val listView: ListView = findViewById(R.id.movie_list)
        listView.adapter = adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val movie: Movie = parent.getItemAtPosition(position) as Movie
            val intent = Intent(this, EditActivity::class.java).apply {
                putExtra("service", srv)
                putExtra("movie", movie)
            }
            startActivityForResult(intent, 2)
        }
    }

    fun handleAddActivity(view: View) {
        val intent = Intent(this, CreateActivity::class.java).apply {
            putExtra("service", srv)
        }
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val movie = data?.getSerializableExtra("data") as Movie
        when (resultCode) {
            1 -> srv.createFilm(movie)
            2 -> srv.updateFilm(movie)
            3 -> srv.deleteFilm(movie)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        logd("On start called")
    }

    override fun onResume() {
        super.onResume()
        logd("On resume called")
    }

    override fun onPause() {
        super.onPause()
        logd("On pause called")
    }

    override fun onStop() {
        super.onStop()
        logd("On stop called")
    }

    override fun onRestart() {
        super.onRestart()
        logd("On restart called")
    }
}
//
//class SimpleItemRecyclerViewAdapter(
//    private val values: List<Movie>,
//    private val srv: Service
//) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {
//
//    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
//        val movie = v.tag as Movie
//        val intent = Intent(v.context, EditActivity::class.java).apply {
//            putExtra("service", srv)
//            putExtra("movie", movie)
//        }
//        v.context.startActivity(intent)
////        startActivityForResult(intent, 2)
//    }
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): SimpleItemRecyclerViewAdapter.ViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: SimpleItemRecyclerViewAdapter.ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//    }
//}