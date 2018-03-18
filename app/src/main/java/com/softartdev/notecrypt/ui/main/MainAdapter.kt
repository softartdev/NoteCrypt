package com.softartdev.notecrypt.ui.main

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.di.ConfigPersistent
import com.softartdev.notecrypt.model.Note
import io.realm.RealmResults
import javax.inject.Inject

@ConfigPersistent
class MainAdapter @Inject
constructor() : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    private var mNotes: RealmResults<Note>? = null
    private var mMainView: MainView? = null

    fun setNotes(notes: RealmResults<Note>, mainView: MainView) {
        mNotes = notes
        mMainView = mainView
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var itemCardView: CardView? = null
        var titleTextView: TextView? = null

        init {
            itemCardView = v.findViewById(R.id.item_note_card_view)
            titleTextView = v.findViewById(R.id.item_note_title_text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = mNotes?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = mNotes?.get(position)
        holder.titleTextView?.text = note?.title
        holder.itemCardView?.setOnClickListener { note?.let { mMainView?.onNote(it.id) } }
    }
}
