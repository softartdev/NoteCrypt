package com.softartdev.notecrypt.ui.settings.security;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.softartdev.notecrypt.R;

public class SecurityActivity extends AppCompatActivity implements SecurityView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    SecurityPresenter mPresenter;
    Switch enableEncryptionSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        mPresenter = new SecurityPresenter(this);
        enableEncryptionSwitch = (Switch) findViewById(R.id.enable_encryption_switch);
        enableEncryptionSwitch.setChecked(mPresenter.isEncryption());
        enableEncryptionSwitch.setOnCheckedChangeListener(this);
        Button passButton = (Button) findViewById(R.id.set_password_button);
        passButton.setOnClickListener(this);
    }

    @Override
    public void showEncryptEnable(boolean encryption) {
        enableEncryptionSwitch.setChecked(encryption);
    }

    @Override
    public void onPass() {
        if (enableEncryptionSwitch.isChecked()) {
            showChangePasswordDialog();
        } else {
            showSetPasswordDialog();
        }
    }

    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_password, null);
        final TextInputLayout enterPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.enter_password_text_input_layout);
        final EditText enterPassEditText = (EditText) view.findViewById(R.id.enter_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDirector pass = new PassMediator(enterPassTextInputLayout, enterPassEditText);
                        if (mPresenter.enterPass(pass)) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_set_password, null);
        final TextInputLayout setPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.set_password_text_input_layout);
        final EditText setPassEditText = (EditText) view.findViewById(R.id.set_password_edit_text);
        final TextInputLayout repeatSetPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.repeat_set_password_text_input_layout);
        final EditText repeatSetPassEditText = (EditText) view.findViewById(R.id.repeat_set_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDirector pass = new PassMediator(setPassTextInputLayout, setPassEditText);
                        DialogDirector repeatPass = new PassMediator(repeatSetPassTextInputLayout, repeatSetPassEditText);
                        if (mPresenter.setPass(pass, repeatPass)) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_change_password, null);
        final TextInputLayout oldPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.old_password_text_input_layout);
        final EditText oldPassEditText = (EditText) view.findViewById(R.id.old_password_edit_text);
        final TextInputLayout newPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.new_password_text_input_layout);
        final EditText newPassEditText = (EditText) view.findViewById(R.id.new_password_edit_text);
        final TextInputLayout repeatPassTextInputLayout = (TextInputLayout) view.findViewById(R.id.repeat_new_password_text_input_layout);
        final EditText repeatNewPassEditText = (EditText) view.findViewById(R.id.repeat_new_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogDirector oldPass = new PassMediator(oldPassTextInputLayout, oldPassEditText);
                        DialogDirector newPass = new PassMediator(newPassTextInputLayout, newPassEditText);
                        DialogDirector repeatNewPass = new PassMediator(repeatPassTextInputLayout, repeatNewPassEditText);
                        if (mPresenter.changePass(oldPass, newPass, repeatNewPass)) {
                            alertDialog.dismiss();
                        }
                    }
                });
            }
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.enable_encryption_switch) {
            if (isChecked) {
                if (!mPresenter.isEncryption()) {
                    mPresenter.pass();
                }
            } else {
                if (mPresenter.isEncryption()) {
                    showPasswordDialog();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_password_button:
                mPresenter.pass();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(this, NavUtils.getParentActivityIntent(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
