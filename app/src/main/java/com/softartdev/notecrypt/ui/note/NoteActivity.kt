package com.softartdev.notecrypt.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.content_note.*
import javax.inject.Inject

class NoteActivity : BaseActivity(), NoteView {
    @Inject lateinit var mPresenter: NotePresenter

    companion object {
        const val NOTE_ID = "note_id"

        fun getStartIntent(context: Context, noteId: Long): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(NOTE_ID, noteId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        activityComponent().inject(this)
        mPresenter.attachView(this)

        setSupportActionBar(note_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        save_note_fab.setOnClickListener { mPresenter.saveNote(note_title_edit_text.text.toString(), note_edit_text.text.toString()) }

        val noteId = intent.getLongExtra(NOTE_ID, 0L)
        if (noteId != 0L) {
            mPresenter.loadNote(noteId)
        } else {
            mPresenter.createNote()
        }
    }

    override fun onLoadNote(title: String, text: String) {
        note_title_edit_text.setText(title)
        supportActionBar?.title = title
        note_edit_text.setText(text)
    }

    override fun onSaveNote(title: String) {
        val noteSaved = getString(R.string.note_saved) + ": " + title
        Snackbar.make(save_note_fab, noteSaved, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    override fun onEmptyNote() {
        Snackbar.make(save_note_fab, R.string.note_empty, Snackbar.LENGTH_LONG).show()
    }

    override fun onDeleteNote() {
        Snackbar.make(save_note_fab, R.string.note_deleted, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                checkSaveChange()
                true
            }
            R.id.action_delete_note -> {
                showDeleteDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        checkSaveChange()
    }

    private fun checkSaveChange() {
        mPresenter.checkSaveChange(note_title_edit_text.text.toString(), note_edit_text.text.toString())
    }

    private fun showDeleteDialog() {
        with(AlertDialog.Builder(this)) {
            setTitle(R.string.action_delete_note)
            setMessage(R.string.note_delete_dialog_message)
            setPositiveButton(android.R.string.yes) { _, _ -> mPresenter.deleteNote() }
            setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    override fun onCheckSaveChange() {
        with(AlertDialog.Builder(this)) {
            setTitle(R.string.note_changes_not_saved_dialog_title)
            setMessage(R.string.note_save_change_dialog_message)
            setPositiveButton(R.string.yes) { _, _ ->
                mPresenter.saveNote(note_title_edit_text.text.toString(), note_edit_text.text.toString())
                onNavBack()
            }
            setNegativeButton(R.string.no) { _, _ -> onNavBack() }
            setNeutralButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
            show()
        }
    }

    override fun onNavBack() {
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}
