package com.softartdev.notecrypt.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.model.Note;
import com.softartdev.notecrypt.ui.BaseActivity;
import com.softartdev.notecrypt.ui.note.NoteActivity;
import com.softartdev.notecrypt.ui.note.NoteAdapter;

import io.realm.RealmResults;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {
    MainPresenter mPresenter;

    RecyclerView notesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        TextView addNoteTextView = (TextView) findViewById(R.id.add_note_text_view);
        addNoteTextView.append("\n" + getResources().getConfiguration().locale);

        notesRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        mPresenter.updateNotes();
    }

    @Override
    public void onUpdateNotes(RealmResults<Note> notes) {
        NoteAdapter noteAdapter = new NoteAdapter(notes);
        notesRecyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onAddNote() {
        startActivity(new Intent(this, NoteActivity.class));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab) {
            mPresenter.addNote();
        }
    }
}
