package com.example.meetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(View view) {
        Runnable post = () ->{
            EditText username = (EditText) findViewById(R.id.username);
            EditText password = (EditText) findViewById(R.id.password);


            try {
                Connection connection=new Connection();
              InputStream inputstream= connection.connect("http://10.0.2.2:5000/login","POST",
                        "{\"username\":\""+username.getText().toString().trim()+"\",\"password\":\""+password.getText().toString().trim()+"\"}");


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
                    String[] split_string = token.split("\\.");
                    byte[] base64EncodedBody = split_string[1].getBytes();
                    byte[] decodedbody=Base64.getDecoder().decode(base64EncodedBody);
                    String bodystring=new String(decodedbody, StandardCharsets.UTF_8);
                    JSONObject jsonobject=new JSONObject(bodystring);
                    expiresIn=(int)jsonobject.get("exp");
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
