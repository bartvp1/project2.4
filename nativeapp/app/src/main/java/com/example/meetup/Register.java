package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.example.meetup.Login.prefsManager;

public class Register extends AppCompatActivity {
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    public void register(View view) {




            //check for fields
           String username = ((EditText) findViewById(R.id.username_reg)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.password_reg)).getText().toString().trim();
            String firstname = ((EditText) findViewById(R.id.firstname)).getText().toString().trim();
            String lastname = ((EditText) findViewById(R.id.lastname)).getText().toString().trim();
            String phone = ((EditText) findViewById(R.id.phonenumber)).getText().toString().trim();
            String country = ((EditText) findViewById(R.id.Country)).getText().toString().trim();
            String city = ((EditText) findViewById(R.id.City)).getText().toString().trim();

        if(username.equals("") || password.equals("")){
            setError("Fill in atleast an username and a password.");
            return;
        }
      if(username.length()<6 || password.length()<6){
          setError("Username and password must atleast be six characters long.");
          return;
        }

        Runnable post = () ->{
            boolean registered=false;
            //register
            try {
                URL url = new URL("http://10.0.2.2:5000/api/register");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout(20000);
                urlConnection.setConnectTimeout(2000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                //send request
                // TODO: 15/06/2020 send register info to rest api
                writer.write("");

                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                Log.d("code",String.valueOf(responseCode));
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    registered=true;

                } else {
                    setError("The user already exists.");
                }

            } catch (Exception e) {
                setError("No connection.");
            }



            




            //login after register
            if(registered) {
                try {
                    URL url = new URL("http://10.0.2.2:5000/api/login");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setReadTimeout(20000);
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    OutputStream os = urlConnection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    writer.write("name=" + username + "&password=" + password);

                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = urlConnection.getResponseCode();
                    Log.d("code", String.valueOf(responseCode));
                    if (responseCode == HttpURLConnection.HTTP_OK) {

                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        JsonReader jsonReader = new JsonReader(in);
                        String token = "";
                        int expiresIn = 0;
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("token")) {
                                token = jsonReader.nextString();
                            } else if (name.equals("expiresIn")) {
                                expiresIn = jsonReader.nextInt();
                            } else {
                                jsonReader.skipValue();
                            }
                        }


                        jsonReader.endObject();


                        in.close();
                        SharedPreferences.Editor editor = prefsManager.getEditor();
                        //set token and expiration
                        editor.putString("token", token);
                        editor.putInt("expiration", expiresIn);
                        editor.commit();

                        goToApp();


                    }

                } catch (Exception e) {
                    setError("Registered correctly, but no connection after trying to log you in.");
                }
            }
        };
        new Thread(post).start();
    }
    public void goToApp(){
        this.runOnUiThread(()->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void setError(String error){
        this.runOnUiThread(()->{
            TextView err=(TextView)findViewById(R.id.error);
            err.setText(error);
        });
    }
}