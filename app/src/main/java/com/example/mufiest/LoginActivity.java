package com.example.mufiest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailBox, passwordBox;
    private LinearLayout loginButton;
    private FirebaseAuth auth;
    private TextView registerTextView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){

            navigateToHomePage();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailBox = findViewById(R.id.emailBox);
        passwordBox =  findViewById(R.id.passwordBox);
        loginButton = findViewById(R.id.loginButton);
        registerTextView = findViewById(R.id.registerTextView);

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToRegisterPage();

            }
        });

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email, password;

                email = String.valueOf(emailBox.getText());
                password = String.valueOf(passwordBox.getText());

                performLogin(email, password);
            }
        });
    }

    private void navigateToRegisterPage() {
        Intent registerPage = new Intent(this, RegisterActivity.class);
        startActivity(registerPage);
        finish();
    }

    private void navigateToHomePage() {
        Intent homePage = new Intent(this, MainActivity.class);
        startActivity(homePage);
        finish();
    }

    private void performLogin(String email, String password) {

        if (!isValidEmail(email) || email.trim().equals("")) {
            Toast.makeText(LoginActivity.this, "Email invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty() || password.trim().equals("")) {
            Toast.makeText(LoginActivity.this, "Password invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                            navigateToHomePage();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
