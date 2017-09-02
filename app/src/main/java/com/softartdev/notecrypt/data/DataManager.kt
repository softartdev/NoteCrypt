package com.softartdev.notecrypt.data

import android.support.annotation.Nullable
import com.softartdev.notecrypt.db.DbStore
import com.softartdev.notecrypt.model.Note
import io.reactivex.Single
import io.realm.RealmResults
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager @Inject
internal constructor(private val dbStore: DbStore) {

    fun notes(): Single<RealmResults<Note>> {
        return Single.fromCallable { dbStore.notes }
    }

    fun createNote(): Single<Note> {
        return Single.fromCallable { dbStore.createNote() }
    }

    fun saveNote(id: Long, title: String, text: String): Single<Unit> {
        return Single.fromCallable { dbStore.saveNote(id, title, text) }
    }

    fun loadNote(noteId: Long): Single<Note> {
        return Single.fromCallable { dbStore.loadNote(noteId) }
    }

    fun deleteNote(id: Long): Single<Unit> {
        return Single.fromCallable { dbStore.deleteNote(id) }
    }

    fun checkPass(pass: String?): Boolean {
        return dbStore.checkPass(pass)
    }

    fun isEncryption(): Boolean {
        return dbStore.isEncryption
    }

    fun changePass(@Nullable odlPass: String?, @Nullable newPass: String?): Single<Unit> {
        return Single.fromCallable { dbStore.changePass(odlPass, newPass) }
    }

    fun checkChanges(id: Long, title: String, text: String): Boolean {
        return dbStore.isChanged(id, title, text)
    }

    fun emptyNote(id: Long): Boolean {
        return dbStore.isEmpty(id)
    }
}
