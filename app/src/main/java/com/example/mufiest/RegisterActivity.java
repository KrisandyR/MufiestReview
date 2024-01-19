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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameBox, emailBox, passwordBox;
    private LinearLayout registerButton;
    private TextView loginText;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent homePage = new Intent(this, MainActivity.class);

            startActivity(homePage);

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameBox = findViewById(R.id.usernameBox);
        emailBox = findViewById(R.id.emailBox);
        passwordBox =  findViewById(R.id.passwordBox);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginTextView);

        Intent loginPage = new Intent(this, LoginActivity.class);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(loginPage);

                finish();
            }
        });

        auth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username, email, password;

                username = String.valueOf(usernameBox.getText());
                email = String.valueOf(emailBox.getText());
                password = String.valueOf(passwordBox.getText());

                if (username.trim().isEmpty() || username.trim().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Username Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (email.isEmpty() || !isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.trim().isEmpty() || password.trim().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Save user data to the Firebase Realtime Database
                                    saveUserDataToDatabase(username, email, password);

                                    Toast.makeText(RegisterActivity.this, "Login Success",
                                            Toast.LENGTH_SHORT).show();

                                    startActivity(loginPage);

                                    finish();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration Failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void saveUserDataToDatabase(String username, String email, String password) {

        String userId = databaseReference.push().getKey();
        DatabaseReference userRef = databaseReference.child(userId);

        // Save user data to the database
        userRef.child("email").setValue(email);
        userRef.child("password").setValue(password);
        userRef.child("userId").setValue(userId);
        userRef.child("username").setValue(username);

    }
}
