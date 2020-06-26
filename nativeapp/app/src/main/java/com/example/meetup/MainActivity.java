package com.example.meetup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.meetup.ui.main.fragments.AccountFragment;
import com.example.meetup.ui.main.fragments.MatchesFragment;
import com.example.meetup.ui.main.fragments.NotificationsFragment;
import com.example.meetup.ui.main.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import static com.example.meetup.Login.prefsManager;


public class MainActivity extends AppCompatActivity {

    ArrayList<Hobby> hobbies = new ArrayList<Hobby>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!prefsManager.isLoggedIn())  logOut();

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

            Runnable logoutrunable=()-> {
                //blacklist on server
                Connection connection = new Connection();
                try {


                    HttpURLConnection urlConnection = (HttpURLConnection) connection.connect("http://10.0.2.2:5000/logout", "POST");


                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    writer.write("{}");

                    writer.flush();
                    writer.close();
                    os.close();


                    urlConnection.disconnect();

                    SharedPreferences.Editor editor = prefsManager.getEditor();
                    //set token and expiration
                    editor.putString("token", null);
                    editor.putInt("expiration", 0);
                    editor.commit();
                    this.runOnUiThread(()-> {
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    });
                }
                //logout with no connection, no  blacklist
                catch (SocketTimeoutException s) {
                    SharedPreferences.Editor editor = prefsManager.getEditor();
                    //set token and expiration
                    editor.putString("token", null);
                    editor.putInt("expiration", 0);
                    editor.commit();
                    this.runOnUiThread(()-> {
                        Intent intent = new Intent(this, Login.class);
                        startActivity(intent);
                        finish();
                    });
                }
                catch (Exception e){

                }
            };
            Thread logouthread=new Thread(logoutrunable);
            logouthread.start();


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



    private void refreshHobbies(String searchHobby){
        hobbies.clear();
        Runnable get = () ->{

            try {
                Connection connection=new Connection();
                HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/hobbies","GET");

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    JsonReader jsonReader = new JsonReader(in);

                    jsonReader.beginArray();

                    while (jsonReader.hasNext()) {

                        jsonReader.beginObject();
                        boolean addhobby=true;

                        Hobby hobby = new Hobby();

                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("name") ) {
                                String hobbyname=jsonReader.nextString();
                                if(!hobbyname.contains(searchHobby)){
                                    addhobby=false;
                                }

                                hobby.setName(hobbyname);
                            } else if (name.equals("id") ) {
                                hobby.setId(jsonReader.nextInt());
                            } else {
                                jsonReader.skipValue();
                            }
                        }
                        if(addhobby) {
                            hobby.setName(hobby.getName().toLowerCase());

                            Hobby hobbytoadd=hobby;
                            Log.d("hobby",hobby.getName());
                            this.runOnUiThread(()->{
                                hobbies.add(hobbytoadd);
                            });
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    in.close();
                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s){
                setHobbiesNotFound("No connection");
            }
            catch (Exception e) {
                Log.d("error",e.getMessage());
            }
        };
        new Thread(get).start();
    }

    private void setHobbiesNotFound(String mess) {
        this.runOnUiThread(()->{
            TextView err=(TextView)findViewById(R.id.error);
            err.setText(mess);
        });
    }

    public void searchHobby(View view){
        CharSequence sequence =  ((TextView) findViewById(R.id.hobbysearchfield) ).getText();
        String searchstring=sequence.toString();
        refreshHobbies(searchstring);


        ListView hobbylistview = findViewById(R.id.hobbylistview);
        ArrayAdapter adapter = new ArrayAdapter<Hobby>(this.getApplicationContext(), android.R.layout.simple_list_item_1, hobbies);
        hobbylistview.setAdapter(adapter);

        hobbylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Hobby hobby=(Hobby)parent.getItemAtPosition(position);

                    displayALert(hobby);
            }
        });
        adapter.notifyDataSetChanged();

    }
    public void displayALert(Hobby hobby){
        Context context=this;

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Add " + hobby.getName().toLowerCase() + " to your profile?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addHobbyToProfile(hobby);
                        dialog.cancel();
                        Toast.makeText(context,"Hobby added",Toast.LENGTH_SHORT).show();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void addHobbyToProfile(Hobby hobby){
        hobbies.clear();
        Runnable get = () ->{

            try {
                Connection connection=new Connection();
                HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/user/me/hobbies/" + hobby.getId(),"POST");


                int responseCode = urlConnection.getResponseCode();

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write("{}");

                writer.flush();
                writer.close();
                os.close();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s){
                setHobbiesNotFound("No connection");
            }
            catch (Exception e) {
                Log.d("error",e.getMessage());

            }
        };
        new Thread(get).start();
    }
}