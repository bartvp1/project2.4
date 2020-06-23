package com.example.meetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.meetup.ui.main.fragments.AccountFragment;
import com.example.meetup.ui.main.fragments.MatchesFragment;
import com.example.meetup.ui.main.fragments.NotificationsFragment;
import com.example.meetup.ui.main.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import static com.example.meetup.Login.prefsManager;


public class MainActivity extends AppCompatActivity {

    ArrayList hobbies = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!prefsManager.isLoggedIn())  logOut();

        BottomNavigationView nav = findViewById(R.id.navigation_view);
        nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        hobbies.add("Tennis");
        hobbies.add("PingPong");
        hobbies.add("Horse Riding");
        hobbies.add("Squash");
        hobbies.add("Swimming");

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accountSettingsButton:
                loadFragment(new AccountFragment());
                return true;
            case R.id.appSettingsButton:
                //loadFragment(new AppSettingsFragment());
                return true;
            case R.id.logoutButton:
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void logOut() {
        this.runOnUiThread(() -> {
            SharedPreferences.Editor editor = prefsManager.getEditor();
            //set token and expiration
            editor.putString("token", null);
            editor.putInt("expiration", 0);
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
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

    public void searchHobby(View view){
        ArrayAdapter adapter = new ArrayAdapter<String>(this.getApplicationContext(), android.R.layout.simple_list_item_1, hobbies);
        ListView hobbylistview = (ListView)findViewById(R.id.hobbylistview);
        hobbylistview.setAdapter(adapter);
    }

    public void hobbySelected(View view){
        view.
    }
}