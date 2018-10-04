package com.kgb.noteseditor.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kgb.noteseditor.database.NoteEntity
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import com.kgb.noteseditor.EditorActivity
import com.kgb.noteseditor.R
import com.kgb.noteseditor.databinding.NoteListItemBinding
import com.kgb.noteseditor.utilities.Constants


class NotesAdapter(private val notes: List<NoteEntity>) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {

        val binding : NoteListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.note_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.note = notes[position]
        holder.binding.fab.setOnClickListener {
            val intent = Intent(it.context, EditorActivity::class.java)
            intent.putExtra(Constants.NOTE_ID_KEY, holder.binding.note?.id)
            it.context.startActivity(intent)
        }
    }

    class ViewHolder(val binding: NoteListItemBinding) : RecyclerView.ViewHolder(binding.root)
}