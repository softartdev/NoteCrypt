package com.softartdev.notecrypt.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.ui.main.MainActivity;

public class SignInActivity extends AppCompatActivity implements SignInView, TextView.OnEditorActionListener, View.OnClickListener {
    SignInPresenter mPresenter;
    TextInputLayout passInputLayout;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mPresenter = new SignInPresenter(this);
        passInputLayout = (TextInputLayout) findViewById(R.id.password_text_input_layout);
        passEditText = (EditText) findViewById(R.id.password_edit_text);
        passEditText.setOnEditorActionListener(this);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
    }

    private void attemptSignIn() {
        mPresenter.signIn(passEditText.getText().toString());
    }

    @Override
    public void navMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void hideError() {
        passInputLayout.setError(null);
    }

    @Override
    public void showEmptyPassError() {
        passInputLayout.setError(getString(R.string.empty_password));
    }

    @Override
    public void showIncorrectPassError() {
        passInputLayout.setError(getString(R.string.incorrect_password));
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

