package com.kgb.noteseditor.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.kgb.noteseditor.database.AppRepository
import com.kgb.noteseditor.database.NoteEntity
import java.util.concurrent.Executors

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppRepository.getInstance(application)
    val notes : LiveData<List<NoteEntity>> = repository.notes

    fun addSampleData() {
        repository.addSampleData()
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}