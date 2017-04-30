package com.softartdev.notecrypt.ui.settings.security;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
        final EditText enterPassEditText = (EditText) view.findViewById(R.id.enter_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = enterPassEditText.getText().toString();
                        mPresenter.enterPass(pass);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_set_password, null);
        final EditText setPassEditText = (EditText) view.findViewById(R.id.set_password_edit_text);
        final EditText repeatSetPassEditText = (EditText) view.findViewById(R.id.repeat_set_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String pass = setPassEditText.getText().toString();
                        String repeatPass = repeatSetPassEditText.getText().toString();
                        mPresenter.setPass(pass, repeatPass);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.dialog_change_password, null);
        final EditText oldPassEditText = (EditText) view.findViewById(R.id.old_password_edit_text);
        final EditText newPassEditText = (EditText) view.findViewById(R.id.new_password_edit_text);
        final EditText repeatNewPassEditText = (EditText) view.findViewById(R.id.repeat_new_password_edit_text);
        builder.setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPass = oldPassEditText.getText().toString();
                        String newPass = newPassEditText.getText().toString();
                        String repeatNewPass = repeatNewPassEditText.getText().toString();
                        mPresenter.changePass(oldPass, newPass, repeatNewPass);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
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
