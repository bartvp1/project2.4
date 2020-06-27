package com.example.meetup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.example.meetup.Login.prefsManager;

public class Register extends AppCompatActivity {
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register(View view) {




            //check for fields
            String username = ((EditText) findViewById(R.id.username_reg)).getText().toString().trim();
            String password = ((EditText) findViewById(R.id.password_reg)).getText().toString().trim();
            String firstname = ((EditText) findViewById(R.id.firstname)).getText().toString().trim();
            String lastname = ((EditText) findViewById(R.id.lastname)).getText().toString().trim();
            String phone = ((EditText) findViewById(R.id.constphonenumber)).getText().toString().trim();
            String country = ((EditText) findViewById(R.id.Country)).getText().toString().trim();
            String city = ((EditText) findViewById(R.id.City)).getText().toString().trim();



        Runnable post = () ->{
            boolean registered=false;
            //register
            try {
                Connection connection=new Connection();
                HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/signup","POST");



                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

               writer.write("{" +
                       "\"username\":\"" + username + "\""+
                       ",\"password\":\""+password +"\""+
                       ",\"firstname\":\""+firstname+"\""+
                       ",\"lastname\":\""+lastname+"\""+
                       ",\"phone\":\""+phone+"\""+
                       ",\"country\":\""+country+"\""+
                       ",\"city\":\""+city+"\""+
                       "}");

                writer.flush();
                writer.close();
                os.close();
                int responseCode = urlConnection.getResponseCode();


                if (responseCode == HttpURLConnection.HTTP_OK) {


                    login(username,password);



                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                    JsonReader jsonReader=new JsonReader(in);
                    jsonReader.beginObject();
                    String errorinput="";
                    while (jsonReader.hasNext()){
                        String name = jsonReader.nextName();
                        if(name.equals("message")){
                            errorinput+=jsonReader.nextString();
                        }
                        else {
                            jsonReader.skipValue();
                        }
                    }

                    jsonReader.endObject();
                    String[] split_string = errorinput.split(",");
                    setError(split_string[0]);
                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s){
                setError("No connection");
            }
            catch (Exception e) {
                Log.d("error",e.getMessage());

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void login(String username, String password){
        try {
            Connection connection=new Connection();
            HttpURLConnection urlConnection=  (HttpURLConnection) connection.connect("http://10.0.2.2:5000/login","POST");



            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            writer.write("{\"username\":\""+username+"\",\"password\":\""+password+"\"}");

            writer.flush();
            writer.close();
            os.close();
            int responseCode = urlConnection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
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
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                JsonReader jsonReader=new JsonReader(in);
                jsonReader.beginObject();
                while (jsonReader.hasNext()){
                    String name = jsonReader.nextName();
                    if(name.equals("message")){
                        setError(jsonReader.nextString());
                    }
                    else {
                        jsonReader.skipValue();
                    }
                }



                jsonReader.endObject();

            }
            urlConnection.disconnect();
        } catch (SocketTimeoutException s){
            setError("No connection");
        }
        catch (Exception e) {
            Log.d("error",e.getMessage());

        }




    }
}