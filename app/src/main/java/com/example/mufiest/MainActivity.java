package com.example.mufiest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.mufiest.controller.MovieController;
import com.example.mufiest.controller.ReviewController;
import com.example.mufiest.fragments.HomeFragment;
import com.example.mufiest.fragments.MovieFragment;
import com.example.mufiest.fragments.MovieScrollList;
import com.example.mufiest.fragments.ReviewWithPosterList;
import com.example.mufiest.models.Movie;
import com.example.mufiest.models.ReviewWithDetail;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.nav_drawer_layout);
        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);

        Drawable drawable = toggle.getDrawerArrowDrawable();
        drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

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
                transaction.replace(R.id.content_container, new HomeFragment());
                transaction.commit();
                navView.setCheckedItem(R.id.home_menu);
                break;
            case R.id.movies_menu:
                transaction.replace(R.id.content_container, new MovieFragment());
                transaction.commit();
                navView.setCheckedItem(R.id.movies_menu);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}