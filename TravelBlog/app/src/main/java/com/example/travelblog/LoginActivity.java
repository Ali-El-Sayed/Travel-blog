package com.example.travelblog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout textUsernameLayout;
    private TextInputLayout textPasswordInput;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUsernameLayout = findViewById(R.id.textUsernameLayout);
        textPasswordInput = findViewById(R.id.textPasswordInput);
        loginButton = findViewById(R.id.loginButton);


        // Java 8
        // loginButton.setOnClickListener(v -> onLoginClicked());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.onLoginClicked();
            }
        });

        textUsernameLayout
                .getEditText()
                .addTextChangedListener(createTextWatcher(textUsernameLayout));
        textPasswordInput.
                getEditText()
                .addTextChangedListener(createTextWatcher(textPasswordInput));

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
        String username = textUsernameLayout.getEditText().getText().toString();
        String password = textPasswordInput.getEditText().getText().toString();

        if (username.isEmpty())
            textUsernameLayout.setError("Username must not be empty");
        if (password.isEmpty())
            textPasswordInput.setError("Password must not be empty");
        else if (!username.equals("admin") && !password.equals("admin"))
            showErrorDialog();

//        Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Clicked", Snackbar.LENGTH_LONG).show();
//        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Faild")
                .setMessage("Username or password is not correct. Please try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}