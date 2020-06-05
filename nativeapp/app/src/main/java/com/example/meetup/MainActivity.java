package com.example.meetup;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.meetup.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public static  String TOKEN;
    public static  int  EXPIRATION;
    private Thread loginchecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Runnable checklogin=()->{
            while (isLoggedIn()){}
            this.runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if(TOKEN!=null) {
                                           logOut();
                                       }
                                   }
                               });

        };
        loginchecker=new Thread(checklogin);
        loginchecker.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
    public void logOutClick(View view){
      logOut();
    }
    public void logOut(){
        this.runOnUiThread(()-> {
            EXPIRATION = 0;
            TOKEN = null;
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }
    public static boolean isLoggedIn(){
        if(TOKEN ==null||( (System.currentTimeMillis()/1000)>EXPIRATION)){
            return false;
        } else {
            return true;
        }
    }



}