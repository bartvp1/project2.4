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



public class Login extends AppCompatActivity {
    public static PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefsManager = PrefsManager.getInstance(this.getApplicationContext());
        if(MainActivity.isLoggedIn()){
            goToApp();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        Runnable post = () ->{
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);
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

                writer.write("name="+username.getText().toString().trim()+"&password="+password.getText().toString().trim());

                writer.flush();
                writer.close();
                os.close();

                int responseCode = urlConnection.getResponseCode();
                Log.d("code",String.valueOf(responseCode));
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    JsonReader jsonReader=new JsonReader(in);
                    String token="";
                    int expiresIn=0;
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equals("token")){
                            token=jsonReader.nextString();
                        }else if(name.equals("expiresIn")){
                            expiresIn=jsonReader.nextInt();
                        }
                        else {
                            jsonReader.skipValue();
                        }
                    }



                    jsonReader.endObject();


                    in.close();
                    SharedPreferences.Editor editor=prefsManager.getEditor();
                    //set token and expiration
                    editor.putString("token",token);
                    editor.putInt("expiration",expiresIn);
                    editor.commit();

                    goToApp();



                } else {
                    //set error message
                    setError("Wrong credentials.");
                }

            } catch (Exception e) {
                Log.d("error",e.getMessage());
                setError("No connection");
            }
        };
        new Thread(post).start();
    }

    public void setError(String error){
        this.runOnUiThread(()->{
            TextView err=(TextView)findViewById(R.id.error);
            err.setText(error);
        });
    }

    public void goToApp(){
        this.runOnUiThread(()->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
    public void goToRegister(View view){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}
