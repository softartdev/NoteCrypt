package com.softartdev.notecrypt.ui.signin

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.softartdev.notecrypt.R
import com.softartdev.notecrypt.ui.base.BaseActivity
import com.softartdev.notecrypt.ui.main.MainActivity

import javax.inject.Inject

class SignInActivity : BaseActivity(), SignInView, TextView.OnEditorActionListener, View.OnClickListener {
    @Inject lateinit var mPresenter: SignInPresenter

    internal var passInputLayout: TextInputLayout? = null
    internal var passEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        activityComponent().inject(this)
        mPresenter.attachView(this)
        passInputLayout = findViewById<TextInputLayout>(R.id.password_text_input_layout)
        passEditText = findViewById<EditText>(R.id.password_edit_text)
        passEditText?.setOnEditorActionListener(this)
        val signInButton = findViewById<Button>(R.id.sign_in_button)
        signInButton.setOnClickListener(this)
    }

    private fun attemptSignIn() {
        mPresenter.signIn(passEditText?.text.toString())
    }

    override fun navMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun hideError() {
        passInputLayout?.error = null
    }

    override fun showEmptyPassError() {
        passInputLayout?.error = getString(R.string.empty_password)
    }

    override fun showIncorrectPassError() {
        passInputLayout?.error = getString(R.string.incorrect_password)
    }

    override fun onEditorAction(textView: TextView, id: Int, keyEvent: KeyEvent): Boolean {
        if (id == R.id.sign_in_ime_action || id == EditorInfo.IME_NULL) {
            attemptSignIn()
            return true
        }
        return false
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.sign_in_button -> attemptSignIn()
        }
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}

