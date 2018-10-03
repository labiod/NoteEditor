package com.kgb.noteseditor.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.kgb.noteseditor.database.AppRepository
import com.kgb.noteseditor.database.NoteEntity

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = AppRepository.getInstance(application)
    var notes : List<NoteEntity> = repository.notes

    fun addSampleData() {
        repository.addSampleData()
    }
}