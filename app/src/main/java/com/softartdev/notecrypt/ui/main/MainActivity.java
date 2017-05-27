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

import io.realm.RealmResults;

import static com.softartdev.notecrypt.model.Note.NOTE_ID;

public class MainActivity extends BaseActivity implements MainView, View.OnClickListener {
    MainPresenter mPresenter;
    TextView addNoteTextView;
    RecyclerView notesRecyclerView;
    Intent onNoteIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new MainPresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addNoteTextView = (TextView) findViewById(R.id.add_note_text_view);

        FloatingActionButton addNoteFab = (FloatingActionButton) findViewById(R.id.add_note_fab);
        addNoteFab.setOnClickListener(this);

        notesRecyclerView = (RecyclerView) findViewById(R.id.notes_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        mPresenter.updateNotes();

        onNoteIntent = new Intent(this, NoteActivity.class);
    }

    @Override
    public void onUpdateNotes(RealmResults<Note> notes) {
        if (notes.size() == 0) {
            addNoteTextView.setVisibility(View.VISIBLE);
        } else {
            addNoteTextView.setVisibility(View.GONE);
            MainAdapter mainAdapter = new MainAdapter(notes, this);
            notesRecyclerView.setAdapter(mainAdapter);
        }
    }

    @Override
    public void onNote(long noteId) {
        onNoteIntent.putExtra(NOTE_ID, noteId);
        startActivity(onNoteIntent);
    }

    @Override
    public void onAddNote() {
        onNoteIntent.putExtra(NOTE_ID, 0L);
        startActivity(onNoteIntent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_note_fab) {
            mPresenter.addNote();
        }
    }
}
