package com.kgb.noteseditor.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.kgb.noteseditor.database.AppRepository
import com.kgb.noteseditor.database.NoteEntity
import java.util.*
import java.util.concurrent.Executors

class EditorViewModel(application: Application) : AndroidViewModel(application) {
    val executor = Executors.newSingleThreadExecutor()
    val liveNote = MutableLiveData<NoteEntity>()
    private val repository = AppRepository.getInstance(application)


    fun loadData(noteId: Int) {
        executor.execute {
            val note = repository.getNoteById(noteId)
            liveNote.postValue(note)
        }
    }

    fun saveNote(noteText: String) {
        val note = liveNote.value
        if (note == null) {
            if (!TextUtils.isEmpty(noteText.trim())) {
                repository.insertNote(NoteEntity(null, Date(), noteText.trim()))
            }

        } else {
            repository.insertNote(NoteEntity(note.id, note.date, noteText))
        }

    }

    fun deleteNote() {
        liveNote.value?.let {
            repository.deleteNote(it)
        }

    }
}