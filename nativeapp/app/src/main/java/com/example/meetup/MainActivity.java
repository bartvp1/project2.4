package com.example.meetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.meetup.ui.main.fragments.MatchesFragment;
import com.example.meetup.ui.main.fragments.NotificationsFragment;
import com.example.meetup.ui.main.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    PrefsManager prefmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        checkLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.navigation_view);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void checkLogin() {
        prefmanager = PrefsManager.getInstance(this.getApplicationContext());
        Runnable checklogin = () -> {
            while (isLoggedIn(prefmanager)) {
            }
            this.runOnUiThread(() -> {
                if (prefmanager.getToken() != null) {
                    logOut();
                }
            });
        };

        Thread loginchecker = new Thread(checklogin);
        loginchecker.start();
    }

    public void logOutClick(View view) {
        logOut();
    }

    public void logOutClick(MenuItem item) {
        logOut();
    }

    public void logOut() {
        this.runOnUiThread(() -> {
            SharedPreferences.Editor editor = prefmanager.getEditor();
            //set token and expiration
            editor.putString("token", null);
            editor.putInt("expiration", 0);
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    public static boolean isLoggedIn(PrefsManager prefmanager) {
        if (prefmanager.getToken() == null || ((System.currentTimeMillis() / 1000) > prefmanager.getExpiration())) {
            return false;
        } else {
            return true;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        getSupportActionBar().setTitle(item.getTitle());
        switch (item.getItemId()) {
            case R.id.botton_notifications:
                loadFragment(new NotificationsFragment());
                return true;
            case R.id.bottom_matches:
                loadFragment(new MatchesFragment());
                return true;
            case R.id.bottom_profile:
                loadFragment(new ProfileFragment());
                return true;
        }
        return false;
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}