package com.softartdev.notecrypt.ui.settings.security;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.softartdev.notecrypt.R;
import com.softartdev.notecrypt.ui.base.BaseActivity;

import javax.inject.Inject;

public class SecurityActivity extends BaseActivity implements SecurityView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    @Inject
    SecurityPresenter mPresenter;

    Switch enableEncryptionSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        activityComponent().inject(this);
        mPresenter.attachView(this);
        enableEncryptionSwitch = findViewById(R.id.enable_encryption_switch);
        enableEncryptionSwitch.setChecked(mPresenter.isEncryption());
        enableEncryptionSwitch.setOnCheckedChangeListener(this);
        Button passButton = findViewById(R.id.set_password_button);
        passButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.enable_encryption_switch) {
            mPresenter.changeEncryption(isChecked);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_password_button:
                mPresenter.changePassword();
                break;
        }
    }

    @Override
    public void showEncryptEnable(boolean encryption) {
        enableEncryptionSwitch.setOnCheckedChangeListener(null);
        enableEncryptionSwitch.setChecked(encryption);
        enableEncryptionSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_password, null);
        final TextInputLayout enterPassTextInputLayout = view.findViewById(R.id.enter_password_text_input_layout);
        final TextInputEditText enterPassEditText = view.findViewById(R.id.enter_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(v -> {
                DialogDirector pass = new PassMediator(enterPassTextInputLayout, enterPassEditText);
                if (mPresenter.enterPassCorrect(pass)) {
                    alertDialog.dismiss();
                }
            });
        });
        alertDialog.show();
    }

    @Override
    public void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_set_password, null);
        final TextInputLayout setPassTextInputLayout = view.findViewById(R.id.set_password_text_input_layout);
        final TextInputEditText setPassEditText = view.findViewById(R.id.set_password_edit_text);
        final TextInputLayout repeatSetPassTextInputLayout = view.findViewById(R.id.repeat_set_password_text_input_layout);
        final TextInputEditText repeatSetPassEditText = view.findViewById(R.id.repeat_set_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create(); //TODO: try to make it global for move positive button listener above
        alertDialog.setOnShowListener(dialog -> {
            Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(v -> {
                DialogDirector pass = new PassMediator(setPassTextInputLayout, setPassEditText);
                DialogDirector repeatPass = new PassMediator(repeatSetPassTextInputLayout, repeatSetPassEditText);
                if (mPresenter.setPassCorrect(pass, repeatPass)) {
                    alertDialog.dismiss();
                }
            });
        });
        alertDialog.show();
    }

    @Override
    public void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_change_password, null);
        final TextInputLayout oldPassTextInputLayout = view.findViewById(R.id.old_password_text_input_layout);
        final TextInputEditText oldPassEditText = view.findViewById(R.id.old_password_edit_text);
        final TextInputLayout newPassTextInputLayout = view.findViewById(R.id.new_password_text_input_layout);
        final TextInputEditText newPassEditText = view.findViewById(R.id.new_password_edit_text);
        final TextInputLayout repeatPassTextInputLayout = view.findViewById(R.id.repeat_new_password_text_input_layout);
        final TextInputEditText repeatNewPassEditText = view.findViewById(R.id.repeat_new_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> {
            Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(v -> {
                DialogDirector oldPass = new PassMediator(oldPassTextInputLayout, oldPassEditText);
                DialogDirector newPass = new PassMediator(newPassTextInputLayout, newPassEditText);
                DialogDirector repeatNewPass = new PassMediator(repeatPassTextInputLayout, repeatNewPassEditText);
                if (mPresenter.changePassCorrect(oldPass, newPass, repeatNewPass)) {
                    alertDialog.dismiss();
                }
            });
        });
        alertDialog.show();
    }

    private class PassMediator implements DialogDirector {
        TextInputLayout mTextInputLayout;
        EditText mEditText;

        PassMediator(TextInputLayout textInputLayout, EditText editText) {
            mTextInputLayout = textInputLayout;
            mEditText = editText;
        }

        @Override
        public String getTextString() {
            return mEditText.getText().toString();
        }

        @Override
        public void showIncorrectPasswordError() {
            mTextInputLayout.setError(getString(R.string.incorrect_password));
        }

        @Override
        public void showEmptyPasswordError() {
            mTextInputLayout.setError(getString(R.string.empty_password));
        }

        @Override
        public void showPasswordsNoMatchError() {
            mTextInputLayout.setError(getString(R.string.passwords_do_not_match));
        }

        @Override
        public void hideError() {
            mTextInputLayout.setError(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        return true;
    }
}
