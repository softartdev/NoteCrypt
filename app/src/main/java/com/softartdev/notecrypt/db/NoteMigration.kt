package com.softartdev.notecrypt.db

import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import java.util.*

internal class NoteMigration : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        // DynamicRealm exposes an editable schema
        val schema = realm.schema

        // Migrate to version 1: Add a dates created & modified.
        if (oldVersion == 0L) {
            schema.get("Note")!!
                    .addField("dateCreated", Date::class.java, FieldAttribute.REQUIRED)
                    .addField("dateModified", Date::class.java, FieldAttribute.REQUIRED)
        }

    }
}
