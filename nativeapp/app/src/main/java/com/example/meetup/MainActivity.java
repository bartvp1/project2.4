package com.example.meetup;

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
import com.example.meetup.ui.main.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



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
    ArrayAdapter adapter;
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



    private void refreshHobbies(String searchstring) {
        hobbies.clear();
        Runnable get = () ->{

            try {
                Connection connection=new Connection();
                HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/hobbies","GET");

                    urlConnection.setConnectTimeout(2000);
                int  responseCode = urlConnection.getResponseCode();






                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    JsonReader jsonReader = new JsonReader(in);

                    jsonReader.beginArray();

                    while (jsonReader.hasNext()) {
                        Hobby hobby = new Hobby();
                        boolean displayhobby=true;
                        jsonReader.beginObject();



                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("name") ) {
                                String hobbyname=jsonReader.nextString();
                                if(!hobbyname.toLowerCase().contains(searchstring.toLowerCase())){
                                    displayhobby=false;
                                }
                                hobby.setName(hobbyname);
                            } else if (name.equals("id") ) {
                                hobby.setId(jsonReader.nextInt());
                            } else {
                                jsonReader.skipValue();
                            }
                        }


                        jsonReader.endObject();
                        if(displayhobby) {
                            this.runOnUiThread(() -> {
                                hobbies.add(hobby);
                                adapter.notifyDataSetChanged();
                            });
                        }

                    }
                    jsonReader.endArray();
                    in.close();
                } else {

                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s){
                noConnection();
            }
            catch (Exception e) {
                Log.d("error",e.getMessage());


            }
        };
        new Thread(get).start();
    }
    public void noConnection(){
        this.runOnUiThread(()-> {
            Toast.makeText(this.getApplicationContext(),"No connection",Toast.LENGTH_SHORT).show();
        });
    }


    public void searchHobby(View view){
        CharSequence sequence =  ((TextView) findViewById(R.id.hobbysearchfield) ).getText();
        String searchstring=sequence.toString();


        ListView hobbylistview = findViewById(R.id.hobbylistview);
        adapter = new ArrayAdapter<Hobby>(this.getApplicationContext(), android.R.layout.simple_list_item_1, hobbies);
        hobbylistview.setAdapter(adapter);
        refreshHobbies(searchstring);

        hobbylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Hobby hobby=(Hobby)parent.getItemAtPosition(position);

                    displayALert(hobby);
            }
        });


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
        Runnable get = () ->{

            try {
                Connection connection=new Connection();
                HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/user/me/hobbies/" + hobby.getId(),"POST");




                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write("{}");

                writer.flush();
                writer.close();
                os.close();
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    this.runOnUiThread(()-> {
                        hobbies.remove(hobby);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this.getApplicationContext(),"Hobby added",Toast.LENGTH_SHORT).show();
                    });
                } else{
                    this.runOnUiThread(()-> {
                    Toast.makeText(this.getApplicationContext(),"Something went wrong, try again later",Toast.LENGTH_SHORT).show();
                    });
                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s){
              noConnection();
            }
            catch (Exception e) {
                Log.d("error",e.getMessage());

            }
        };
        new Thread(get).start();
    }
}