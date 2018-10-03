package com.kgb.noteseditor.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val date: Date,
    val text: String) {

    override fun toString(): String {
        return "NoteEntity(id=$id, date=$date, text='$text')"
    }


}