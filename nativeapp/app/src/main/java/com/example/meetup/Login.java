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
import java.io.InputStream;
import java.io.InputStreamReader;




public class Login extends AppCompatActivity {
    public static PrefsManager prefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefsManager = PrefsManager.getInstance(this.getApplicationContext());
        if(prefsManager.isLoggedIn()){
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
                Connection connection=new Connection();
              InputStream inputstream= connection.connect("http://10.0.2.2:5000/api/login","POST",
                        "name="+username.getText().toString().trim()+"&password="+password.getText().toString().trim());


                if (inputstream!=null) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(inputstream));
                    JsonReader jsonReader=new JsonReader(in);
                    String token=null;
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
