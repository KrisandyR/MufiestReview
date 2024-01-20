package com.example.mufiest.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mufiest.R;
import com.example.mufiest.models.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private TextView textUsername;
    private TextView textEmail;
    private ImageView imageProfile, ciButton;
    private String customUID;
    private FirebaseAuth auth;
    private DatabaseReference userRef;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textUsername = view.findViewById(R.id.textUsername);
        textEmail = view.findViewById(R.id.textEmail);
        imageProfile = view.findViewById(R.id.imageProfile);
        ciButton = view.findViewById(R.id.ciButton);

        ciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(v);
            }
        });

        setupFirebase();

        return view;
    }

    private void setupFirebase() {

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        String userId = currentUser.getUid();
        userRef = FirebaseDatabase.getInstance().getReference("users");

        userRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handleDataSnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleDataSnapshot(DataSnapshot dataSnapshot) {
        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
            customUID = userSnapshot.getKey();
            userRef = FirebaseDatabase.getInstance().getReference("users").child(customUID);

            User user = userSnapshot.getValue(User.class);

            if (user != null) {
                loadUserData(user);
            }
        }
    }

    private void loadUserData(User user) {
        Picasso.get().load(user.getProfileUrl()).into(imageProfile);
        textUsername.setText(user.getUsername());
        textEmail.setText(user.getEmail());
    }

    private void pickImage(View view) {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {

            // Get the selected image URI
            Uri imageUri = data.getData();

            // Load the selected image
            Picasso.get()
                    .load(imageUri)
                    .into(imageProfile);

            // Save the imageUri to the Firebase Realtime Database
            updateProfileImageUri(imageUri);
        }
    }

    private void updateProfileImageUri(Uri imageUri) {
        userRef.child("profileUrl").setValue(imageUri.toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(requireContext(), "Profile image updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to update profile image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
