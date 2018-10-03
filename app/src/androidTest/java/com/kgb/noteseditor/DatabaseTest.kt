package com.kgb.noteseditor

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.kgb.noteseditor.database.AppDatabase
import com.kgb.noteseditor.database.NoteDao
import com.kgb.noteseditor.utilities.SampleData
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    companion object {
        const val TAG = "JUnit"
    }

    lateinit var db : AppDatabase
    lateinit var dao : NoteDao

    @Before
    fun createDB() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.noteDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun createRetrieveNotes() {
        dao.insertAll(SampleData.getNotes())
        val count = dao.getCount()
        assertEquals(SampleData.getNotes().size, count)
    }

    @Test
    fun compareStrings() {
        dao.insertAll(SampleData.getNotes())
        val original = SampleData.getNotes()[0]
        val fromDb = dao.getNoteById(1)
        assertEquals(original.text, fromDb.text)
        assertEquals(1, fromDb.id)
    }
}