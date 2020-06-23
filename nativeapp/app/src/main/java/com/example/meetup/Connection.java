package com.example.meetup;

import android.util.Log;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import static com.example.meetup.Login.prefsManager;

public class Connection {
    //class for connection to rest api with interceptor (adds the jwt token as authorization header)
    //returns an InputStream or null, if no connection/not OK
    /*example how to use :
        Use the connect method in a new thread!!!

        Connection connection=new Connection();
       InputStream stream= connection.connect("http://10.0.2.2:5000/api/hobbies","GET","");
        //use InputStream to read the response body

        update UI with the runOnUiThread() method
     */

    public URLConnection connect(String url_string, String method)  throws SocketTimeoutException {




                try {

                    URL url = new URL(url_string);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type","application/json");
                    String token=prefsManager.getToken();
                    if(token!=null) {
                        urlConnection.setRequestProperty("Authorization", prefsManager.getToken());
                    }
                    urlConnection.setReadTimeout(20000);
                    urlConnection.setConnectTimeout(2000);
                    urlConnection.setRequestMethod(method);
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    return urlConnection;



                }

                catch (Exception e){

                }




            return null;


    }
}
