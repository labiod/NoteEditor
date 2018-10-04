package com.kgb.noteseditor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.kgb.noteseditor.database.NoteEntity
import com.kgb.noteseditor.databinding.ActivityMainBinding
import com.kgb.noteseditor.ui.NotesAdapter
import com.kgb.noteseditor.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val notesData = ArrayList<NoteEntity>()
    lateinit var binding: ActivityMainBinding
    var notesAdapter : NotesAdapter? = null
    var viewModel : MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)
        initRecyclerView()

        binding.setListener { _ ->
            val intent = Intent(this@MainActivity, EditorActivity::class.java)
            startActivity(intent)
        }
        initViewModel()

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
            R.id.action_delete_all -> {
                deleteAllNotes()
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
        val divider = DividerItemDecoration(this, linearManager.orientation)
        recycler_view.addItemDecoration(divider)
    }

    private fun initViewModel() {
        val notesObserver = Observer<List<NoteEntity>> { notes ->
            notesData.clear()
            notes?.let {
                notesData.addAll(it)
            }
            if (notesAdapter == null) {
                notesAdapter = NotesAdapter(notesData)
                recycler_view.adapter = notesAdapter
            } else {
                notesAdapter?.notifyDataSetChanged()
            }


            
        }
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel?.notes?.observe(this, notesObserver)
        binding.viewmodel = viewModel
    }

    private fun deleteAllNotes() {
        viewModel?.deleteAllNotes()
    }
}
