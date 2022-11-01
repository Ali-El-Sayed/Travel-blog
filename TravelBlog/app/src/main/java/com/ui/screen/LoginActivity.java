package com.ui.screen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.peferences.BlogPreferences;
import com.example.travelblog.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout mTextInputLayout;
    private TextInputLayout mTextPasswordInput;
    private Button mButton;
    private ProgressBar mProgressBar;
    private BlogPreferences mBlogPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextInputLayout = findViewById(R.id.textUsernameLayout);
        mTextPasswordInput = findViewById(R.id.textPasswordInput);
        mButton = findViewById(R.id.loginButton);
        mProgressBar = findViewById(R.id.progressBar);

        mBlogPreferences = new BlogPreferences(this);
        if (mBlogPreferences.isLoggedIn()) {
            startMainActivity();
            finish();
            return;
        }

        // Java 8
        // loginButton.setOnClickListener(v -> onLoginClicked());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.onLoginClicked();
            }
        });

        mTextInputLayout.getEditText()
                .addTextChangedListener(createTextWatcher(mTextInputLayout));
        mTextPasswordInput.getEditText()
                .addTextChangedListener(createTextWatcher(mTextPasswordInput));

    }

    private TextWatcher createTextWatcher(TextInputLayout textPasswordInput) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                textPasswordInput.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    ;


    private void onLoginClicked() {
        String username = mTextInputLayout.getEditText().getText().toString();
        String password = mTextPasswordInput.getEditText().getText().toString();

        if (username.isEmpty())
            mTextInputLayout.setError("Username must not be empty");
        else if (password.isEmpty())
            mTextPasswordInput.setError("Password must not be empty");
        else if (!username.equals("admin") && !password.equals("admin"))
            showErrorDialog();
        else
            performLogin();

        Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Clicked", Snackbar.LENGTH_LONG).show();
//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    private void performLogin() {
        mBlogPreferences.setLoggedIn(true);
        mTextInputLayout.setEnabled(false);
        mTextPasswordInput.setEnabled(false);
        mButton.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startMainActivity();
            finish();
        }, 2000);

    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Failed")
                .setMessage("Username or password is not correct. Please try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}