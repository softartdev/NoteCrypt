package com.softartdev.notecrypt.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.model.Note
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.note.NoteActivity
import io.github.tonnyl.spark.Spark
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView, MainAdapter.ClickListener {
    @Inject lateinit var mPresenter: MainPresenter
    @Inject lateinit var mAdapter: MainAdapter

    private lateinit var mSpark: Spark

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent().inject(this)
        mPresenter.attachView(this)

        setSupportActionBar(main_toolbar)

        add_note_fab.setOnClickListener { startActivity(NoteActivity.getStartIntent(this, 0L)) }

        mAdapter.clickListener = this
        notes_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        if (mAdapter.itemCount == 0) {
            mPresenter.updateNotes()
        }

        mSpark = Spark.Builder()
                .setView(main_coordinator) // View or view group
                .setAnimList(Spark.ANIM_BLUE_PURPLE)
                .build()
    }

    override fun onResume() {
        super.onResume()
        mSpark.startAnimation()
    }

    override fun onPause() {
        super.onPause()
        mSpark.stopAnimation()
    }

    override fun onUpdateNotes(notes: List<Note>) {
        add_note_text_view.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        mAdapter.mNotes = notes
    }

    override fun onNoteClick(noteId: Long) {
        startActivity(NoteActivity.getStartIntent(this, noteId))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_release -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
