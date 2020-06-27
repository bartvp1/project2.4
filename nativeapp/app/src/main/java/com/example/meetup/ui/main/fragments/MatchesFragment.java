package com.example.meetup.ui.main.fragments;

import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetup.Connection;
import com.example.meetup.Hobby;
import com.example.meetup.R;
import com.example.meetup.ui.main.RecyclerViewAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    //matchesview
    ArrayList<Match> matches = new ArrayList<>();
    RecyclerView recyclerview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            //initmatches recyclerview
            recyclerview = this.getActivity().findViewById(R.id.matchesrecyclerview);

            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), matches);
            recyclerview.setAdapter(recyclerViewAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));

            loadMatches();

        }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        return root;
    }

    public void loadMatches(){

        Runnable load=()-> {
            try {
                Connection connection = new Connection();
                HttpURLConnection urlConnection = (HttpURLConnection) connection.connect("http://10.0.2.2:5000/user/me/matches/", "GET");

                urlConnection.setConnectTimeout(2000);
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    JsonReader jsonReader = new JsonReader(in);

                    jsonReader.beginArray();

                    while (jsonReader.hasNext()) {
                        Match match = new Match();
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String name = jsonReader.nextName();
                            if (name.equals("naam")) {
                                match.setName(jsonReader.nextString());
                            } else if (name.equals("phone")){
                                match.setPhonenumber(jsonReader.nextString());
                            } else if (name.equals("city")){
                                match.setCity(jsonReader.nextString());
                            } else if (name.equals("hobbySet")){
                                jsonReader.beginArray();
                                ArrayList arrayList = new ArrayList();
                                while(jsonReader.hasNext()){
                                    Hobby hobby = new Hobby();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()){
                                        name = jsonReader.nextName();
                                        if(name.equals("id")){
                                            hobby.setId(jsonReader.nextInt());
                                        } else {
                                            hobby.setName(jsonReader.nextString());
                                        }
                                    }
                                    arrayList.add(hobby);
                                    jsonReader.endObject();
                                }
                                jsonReader.endArray();
                                match.setHobbies(arrayList);

                            } else if (name.equals("sameHobbies")){
                                jsonReader.beginArray();
                                ArrayList arrayList = new ArrayList();
                                while(jsonReader.hasNext()){
                                    Hobby hobby = new Hobby();
                                    jsonReader.beginObject();
                                    while (jsonReader.hasNext()){
                                        name = jsonReader.nextName();
                                        if(name.equals("id")){
                                            hobby.setId(jsonReader.nextInt());
                                        } else {
                                            hobby.setName(jsonReader.nextString());
                                        }
                                    }
                                    arrayList.add(hobby);
                                    jsonReader.endObject();
                                }
                                jsonReader.endArray();
                                match.setSameHobbies(arrayList);
                            } else {
                                jsonReader.skipValue();
                            }
                        }

                        jsonReader.endObject();
                        matches.add(match);
                    }
                    jsonReader.endArray();
                    in.close();

                } else {

                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s) {
                this.getActivity().runOnUiThread(()-> {
                    Toast.makeText(this.getActivity().getApplicationContext(),"No connection",Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                System.out.println(e);


            }
        };
        new Thread(load).start();
    }
    public class Match {
        String name;
        String phonenumber;
        String city;
        ArrayList<String> hobbies;
        ArrayList<String> sameHobbies;

        public Match() {}

        public ArrayList<String> getHobbies() {
            return hobbies;
        }
        public void addHobby(String s){
            hobbies.add(s);
        }

        public void addSameHobby(String s){
            hobbies.add(s);
        }

        public ArrayList<String> getSameHobbies() {
            return sameHobbies;
        }

        public String getCity() {
            return city;
        }

        public String getName() {
            return name;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setHobbies(ArrayList<String> hobbies) {
            this.hobbies = hobbies;
        }

        public void setSameHobbies(ArrayList<String> sameHobbies) {
            this.sameHobbies = sameHobbies;
        }
    }
}
