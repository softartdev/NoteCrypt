package com.softartdev.notecrypt.ui.note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.ui.BaseActivity;

import static com.softartdev.notecrypt.model.Note.NOTE_ID;

public class NoteActivity extends BaseActivity implements NoteView, View.OnClickListener {
    NotePresenter mPresenter;
    FloatingActionButton saveNoteFab;
    EditText noteEditText, titleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        mPresenter = new NotePresenter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        saveNoteFab = (FloatingActionButton) findViewById(R.id.save_note_fab);
        saveNoteFab.setOnClickListener(this);
        noteEditText = (EditText) findViewById(R.id.note_edit_text);
        titleEditText = (EditText) findViewById(R.id.note_title_edit_text);

        Intent intent = getIntent();
        long noteId = intent.getLongExtra(NOTE_ID, 0L);
        if (noteId != 0L) {
            mPresenter.loadNote(noteId);
        } else {
            mPresenter.createNote();
        }
    }

    @Override
    public void onLoadNote(String title, String text) {
        titleEditText.setText(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        noteEditText.setText(text);
    }

    @Override
    public void onSaveNote(String title) {
        String noteSaved = getString(R.string.note_saved) + ": " + title;
        Snackbar.make(saveNoteFab, noteSaved, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onEmptyNote() {
        Snackbar.make(saveNoteFab, R.string.note_empty, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteNote() {
        Snackbar.make(saveNoteFab, R.string.note_deleted, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.save_note_fab) {
            mPresenter.saveNote(titleEditText.getText().toString(), noteEditText.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                checkSaveChange();
                return true;
            case R.id.action_delete_note:
                showDeleteDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        checkSaveChange();
    }

    private void checkSaveChange() {
        mPresenter.checkSaveChange(titleEditText.getText().toString(), noteEditText.getText().toString());
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_delete_note)
                .setMessage(R.string.note_delete_dialog_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.deleteNote();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCheckSaveChange() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.note_changes_not_saved_dialog_title)
                .setMessage(R.string.note_save_change_dialog_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.saveNote(titleEditText.getText().toString(), noteEditText.getText().toString());
                        onNavBack();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onNavBack();
                    }
                })
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onNavBack() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
