package com.example.mufiest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;


import com.example.mufiest.adapters.MovieScrollListAdapter;
import com.example.mufiest.fragments.HomeFragment;
import com.example.mufiest.fragments.MovieFragment;

import com.example.mufiest.fragments.MovieSearchFragment;
import com.example.mufiest.fragments.MovieScrollList;
import com.example.mufiest.fragments.ProfileFragment;
import com.example.mufiest.fragments.ReviewWithPosterList;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MovieScrollListAdapter.OnMovieClickListener, MovieFragment.OnBackButtonClickedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        // Set Up Toolbar
        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        // Set Up Navigation Drawer
        drawerLayout = findViewById(R.id.nav_drawer_layout);
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);

        Drawable drawable = toggle.getDrawerArrowDrawable();
        drawable.setColorFilter(getResources().getColor(R.color.orange), PorterDuff.Mode.SRC_IN);

        // Set Default Fragment to HomeFragment
        if(savedInstanceState == null){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_container, new HomeFragment());
            transaction.commit();
            navView.setCheckedItem(R.id.home_menu);
        }

        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (item.getItemId()){
            case R.id.home_menu:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.content_container, new HomeFragment());
                transaction.commit();
                navView.setCheckedItem(R.id.home_menu);
                break;
            case R.id.movies_menu_search:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.content_container, new MovieSearchFragment());
                transaction.commit();
                navView.setCheckedItem(R.id.movies_menu_search);
                break;
            case R.id.profile_menu:
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                transaction.replace(R.id.content_container, new ProfileFragment());
                transaction.commit();
                navView.setCheckedItem(R.id.profile_menu);
                break;
            case R.id.logout_menu:
                logout();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        auth.signOut();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onMovieClicked(String movieId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_container, MovieFragment.newInstance(movieId, this));
        transaction.addToBackStack(null);
        transaction.commit();
        navView.setCheckedItem(0);
    }

    @Override
    public void onBackButtonClicked() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_container, new HomeFragment());
            transaction.commit();
            navView.setCheckedItem(R.id.home_menu);
        }
    }

}