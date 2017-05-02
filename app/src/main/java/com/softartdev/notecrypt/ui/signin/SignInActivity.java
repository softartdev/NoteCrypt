package com.softartdev.notecrypt.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.ui.main.MainActivity;

public class SignInActivity extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EditText passEditText = (EditText) findViewById(R.id.password_edit_text);
        passEditText.setOnEditorActionListener(this);

        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
    }

    private void attemptSignIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.sign_in_ime_action || id == EditorInfo.IME_NULL) {
            attemptSignIn();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                attemptSignIn();
                break;
        }
    }
}

