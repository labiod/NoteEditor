package com.kgb.noteseditor.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao

    companion object {
        const val DATABASE_NAME = "AppDatabase.db"
        @Volatile
        private var _instance : AppDatabase? = null
        val lock = Any()

        @JvmStatic
        fun getInstance(context: Context) : AppDatabase {
            if (_instance == null) {
                synchronized(lock) {
                    if (_instance == null) {
                        _instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
                    }
                }
            }
            return _instance!!
        }
    }
}