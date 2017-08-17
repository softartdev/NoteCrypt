package com.softartdev.notecrypt.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView

import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.note.NoteActivity

import javax.inject.Inject

import io.realm.RealmResults

import com.softartdev.notecrypt.model.Note.NOTE_ID

class MainActivity : BaseActivity(), MainView, View.OnClickListener {
    @Inject lateinit var mPresenter: MainPresenter
    @Inject lateinit var mAdapter: MainAdapter

    internal var addNoteTextView: TextView? = null
    internal var notesRecyclerView: RecyclerView? = null
    internal var onNoteIntent: Intent = Intent(this, NoteActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent().inject(this)
        mPresenter.attachView(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        addNoteTextView = findViewById<TextView>(R.id.add_note_text_view)

        val addNoteFab = findViewById<FloatingActionButton>(R.id.add_note_fab)
        addNoteFab.setOnClickListener(this)

        notesRecyclerView = findViewById<RecyclerView>(R.id.notes_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        notesRecyclerView?.layoutManager = layoutManager
        notesRecyclerView?.adapter = mAdapter
        if (mAdapter.itemCount == 0) {
            mPresenter.updateNotes()
        }
    }

    override fun onUpdateNotes(notes: RealmResults<Note>) {
        if (notes.size == 0) {
            addNoteTextView?.visibility = View.VISIBLE
        } else {
            addNoteTextView?.visibility = View.GONE
            mAdapter.setNotes(notes, this)
        }
    }

    override fun onNote(noteId: Long) {
        onNoteIntent.putExtra(NOTE_ID, noteId)
        startActivity(onNoteIntent)
    }

    override fun onAddNote() {
        onNoteIntent.putExtra(NOTE_ID, 0L)
        startActivity(onNoteIntent)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.add_note_fab) {
            mPresenter.addNote()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
