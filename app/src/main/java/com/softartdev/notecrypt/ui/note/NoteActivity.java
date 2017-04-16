package com.softartdev.notecrypt.ui.note;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.ui.BaseActivity;

public class NoteActivity extends BaseActivity implements NoteView, View.OnClickListener {
    NotePresenter mPresenter;

    Toolbar toolbar;
    FloatingActionButton fab;
    EditText noteEditText, titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mPresenter = new NotePresenter(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        noteEditText = (EditText) findViewById(R.id.note_edit_text);
        titleEditText = (EditText) findViewById(R.id.note_title_edit_text);
    }

    @Override
    public void onSaveNote(String title) {
        String noteSaved = "Note saved: " + title;
        Snackbar.make(fab, noteSaved, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fab) {
            mPresenter.saveNote(titleEditText.getText().toString(), noteEditText.getText().toString());
        }
    }
}
