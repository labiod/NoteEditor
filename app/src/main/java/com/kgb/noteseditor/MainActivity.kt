package com.kgb.noteseditor

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.kgb.noteseditor.databinding.ActivityMainBinding
import com.kgb.noteseditor.database.NoteEntity
import com.kgb.noteseditor.ui.NotesAdapter
import com.kgb.noteseditor.utilities.SampleData
import com.kgb.noteseditor.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val notesData = ArrayList<NoteEntity>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()

        binding.setListener { _ ->
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
            startActivity(intent)
        }
        binding.viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.viewmodel?.let {
            notesData.addAll(it.notes)
        }
        notesData.forEach { Log.i(MainActivity::class.java.name, it.toString()) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_add_sample_data -> {
               addSampleData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addSampleData() {
        binding.viewmodel?.addSampleData()
    }

    private fun initRecyclerView() {
        recycler_view.setHasFixedSize(true)
        val linearManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearManager
        recycler_view.adapter = NotesAdapter(notesData)
    }
}
