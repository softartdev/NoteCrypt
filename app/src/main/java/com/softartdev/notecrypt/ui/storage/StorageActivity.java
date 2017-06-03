package com.softartdev.notecrypt.ui.storage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.softartdev.notecrypt.R;

import de.jonasrottmann.realmbrowser.RealmBrowser;
import io.realm.Realm;

public class StorageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        Button filesButton = (Button) findViewById(R.id.storage_files_button);
        filesButton.setOnClickListener(this);
        Button tablesButton = (Button) findViewById(R.id.storage_tables_button);
        tablesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.storage_files_button:
                RealmBrowser.startRealmFilesActivity(this);
                break;
            case R.id.storage_tables_button:
                RealmBrowser.startRealmModelsActivity(this, Realm.getDefaultInstance().getConfiguration());
                break;
        }
    }

}
