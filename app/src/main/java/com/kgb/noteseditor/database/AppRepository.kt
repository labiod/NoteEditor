package com.kgb.noteseditor.database

import android.arch.lifecycle.LiveData
import android.content.Context
import com.kgb.noteseditor.utilities.SampleData
import java.util.concurrent.Executors

class AppRepository private constructor(context: Context) {
    private val db = AppDatabase.getInstance(context)
    val notes : LiveData<List<NoteEntity>> = getAllNotes()
    private val executor = Executors.newSingleThreadExecutor()

    fun addSampleData() {
        executor.execute {
            db.noteDao().insertAll(SampleData.getNotes())
        }
    }

    private fun getAllNotes(): LiveData<List<NoteEntity>> {
        return db.noteDao().getAll()
    }

    fun deleteAllNotes() {
        executor.execute {
            db.noteDao().deleteAll()
        }
    }

    fun getNoteById(noteId: Int): NoteEntity {
        return db.noteDao().getNoteById(noteId)
    }

    fun insertNote(note: NoteEntity) {
        executor.execute {
            db.noteDao().insertNote(note)
        }
    }

    fun deleteNote(noteEntity: NoteEntity) {
        executor.execute {
            db.noteDao().deleteNote(noteEntity)
        }
    }

    companion object {
        @Volatile
        var instance : AppRepository? = null
        val lock = Any()

        fun getInstance(context: Context): AppRepository {
            if (instance == null) {
                synchronized(lock) {
                    if (instance == null) {
                        instance = AppRepository(context)
                    }
                }
            }
            return instance!!
        }
    }
}