package com.softartdev.notecrypt.ui.note;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.model.Note;

import io.realm.RealmResults;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private RealmResults<Note> mNotes;

    public NoteAdapter(RealmResults<Note> notes) {
        mNotes = notes;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        ViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.item_note_title_text_view);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.titleTextView.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
