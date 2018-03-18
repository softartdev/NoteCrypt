package com.softartdev.notecrypt.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.model.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@ConfigPersistent
class MainAdapter @Inject
constructor() : RecyclerView.Adapter<MainAdapter.NotesViewHolder>() {
    var mNotes: List<Note> = emptyList()
    var clickListener: ClickListener? = null
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NotesViewHolder(v)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = mNotes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = mNotes.size

    interface ClickListener {
        fun onNoteClick(noteId: Long)
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.item_note_title_text_view.text = note.title
            itemView.item_note_date_text_view.text = simpleDateFormat.format(note.dateModified)
            itemView.setOnClickListener { clickListener?.onNoteClick(note.id) }
        }
    }
}
