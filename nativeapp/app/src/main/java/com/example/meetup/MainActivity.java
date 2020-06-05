package com.example.meetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.example.meetup.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    PrefsManager prefmanager;
    private Thread loginchecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefmanager=PrefsManager.getInstance(this.getApplicationContext());
        Runnable checklogin=()->{
            while (isLoggedIn(prefmanager)){}
            this.runOnUiThread(new Runnable() {
                                   @Override
                                   public void run() {
                                       if(prefmanager.getToken()!=null) {
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
            SharedPreferences.Editor editor=prefmanager.getEditor();
            //set token and expiration
            editor.putString("token",null);
            editor.putInt("expiration",0);
            editor.commit();
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        });
    }
    public static boolean isLoggedIn(PrefsManager prefmanager){


        if(prefmanager.getToken() ==null||( (System.currentTimeMillis()/1000)>prefmanager.getExpiration())){
            return false;
        } else {
            return true;
        }
    }


}