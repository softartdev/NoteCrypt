package com.softartdev.notecrypt.ui.main;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.model.Note;

import io.realm.RealmResults;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private RealmResults<Note> mNotes;
    private MainView mMainView;

    MainAdapter(RealmResults<Note> notes, MainView mainView) {
        mNotes = notes;
        mMainView = mainView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView itemCardView;
        TextView titleTextView;

        ViewHolder(View v) {
            super(v);
            itemCardView = (CardView) v.findViewById(R.id.item_note_card_view);
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
        final Note note = mNotes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainView.onNote(note.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}
