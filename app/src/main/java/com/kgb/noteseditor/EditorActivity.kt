package com.kgb.noteseditor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kgb.noteseditor.databinding.ActivityEditorBinding
import com.kgb.noteseditor.utilities.Constants
import com.kgb.noteseditor.viewmodel.EditorViewModel
import kotlinx.android.synthetic.main.activity_editor.*
import kotlinx.android.synthetic.main.content_editor.*

class EditorActivity : AppCompatActivity() {
    lateinit var viewModel : EditorViewModel
    lateinit var binding: ActivityEditorBinding
    private var newNote = false
    private var edditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_check)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        savedInstanceState?.let {
            edditing = savedInstanceState.getBoolean(Constants.EDITING_KEY, false)
        }
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!newNote) {
            menuInflater.inflate(R.menu.menu_editor, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId ?: 0
        when(id) {
            android.R.id.home -> {
                saveAndReturn()
                return true
            }
            R.id.action_delete -> {
                deleteNote()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        saveAndReturn()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putBoolean(Constants.EDITING_KEY, true)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun saveAndReturn() {
        viewModel.saveNote(note_text.text.toString())
        finish()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)

        viewModel.liveNote.observe(this, Observer {
            if (it != null && !edditing) {
                note_text.setText(it.text)
            }
        })

        if (intent.extras?.containsKey(Constants.NOTE_ID_KEY) == true) {
            title = getString(R.string.edit_note_title)
            val noteId = intent.extras!!.getInt(Constants.NOTE_ID_KEY)
            viewModel.loadData(noteId)
        } else {
            title = getString(R.string.new_note_title)
            newNote = true
        }
    }

    private fun deleteNote() {
        viewModel.deleteNote()
        finish()
    }
}
