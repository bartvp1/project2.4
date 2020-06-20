package com.example.meetup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                Connection connection=new Connection();
                InputStream registerstream=connection.connect("http://10.0.2.2:5000/api/register","POST","");

                if (registerstream!=null) {

                    registered=true;

                } else {
                    setError("The user already exists.");
                }

            } catch (Exception e) {
                setError("No connection.");
            }

            registered=true;

            //login after register
            if(registered) {
                try {
                    Connection connection=new Connection();
                    InputStream loginstream= connection.connect("http://10.0.2.2:5000/api/login","POST",
                            "name="+username+"&password="+password);



                    if (loginstream !=null) {

                        BufferedReader in = new BufferedReader(new InputStreamReader(loginstream));
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