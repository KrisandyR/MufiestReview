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

import com.example.mufiest.models.User;
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
    private static final String DEFAULT_PROFILE_IMAGE = "https://i.imgur.com/V4RclNb.png";

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
                navigateToLoginPage();
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

                registerUser(username, email, password);
            }
        });
    }

    private void navigateToLoginPage() {
        Intent registerPage = new Intent(this,LoginActivity.class);
        startActivity(registerPage);
        finish();
    }

    private void navigateToHomePage() {
        Intent homePage = new Intent(this, MainActivity.class);
        startActivity(homePage);
        finish();
    }


    private void registerUser(String username, String email, String password) {

        if (username.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Username Invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Email Invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Password Invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        saveUserDataToDatabase(username, email, password);

                        Toast.makeText(RegisterActivity.this, "Login Success",
                                Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(this, LoginActivity.class));

                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private void saveUserDataToDatabase(String username, String email, String password) {
        String authId = auth.getCurrentUser().getUid();
        String userId = databaseReference.push().getKey();

        DatabaseReference userRef = databaseReference.child(userId);

        // Save to user model
        User user = new User(authId, username, email, password, DEFAULT_PROFILE_IMAGE);

        // Save user data to the database
        userRef.setValue(user);
    }
}
