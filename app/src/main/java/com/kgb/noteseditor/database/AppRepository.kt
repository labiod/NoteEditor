package com.kgb.noteseditor.database

import android.content.Context
import com.kgb.noteseditor.utilities.SampleData
import java.util.concurrent.Executors

class AppRepository private constructor(context: Context) {
    val notes = SampleData.getNotes()
    private val db = AppDatabase.getInstance(context)
    private val executor = Executors.newSingleThreadExecutor()

    fun addSampleData() {
        executor.execute {
            db.noteDao().insertAll(notes)
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