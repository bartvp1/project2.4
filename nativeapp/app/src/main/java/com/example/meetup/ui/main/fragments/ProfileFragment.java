package com.example.meetup.ui.main.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.meetup.Connection;
import com.example.meetup.Hobby;
import com.example.meetup.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    ArrayList<Hobby> myhobbies = new ArrayList<Hobby>();
    ArrayAdapter<Hobby> myhobbyadapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        myhobbyadapter = new ArrayAdapter<Hobby>(this.getContext(), android.R.layout.simple_list_item_1, myhobbies);
        ListView hobbyview=(ListView) this.getActivity().findViewById(R.id.myhobbieview);
        hobbyview.setAdapter(myhobbyadapter);
        loadMyHobbies();
        hobbyview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                Hobby hobby=(Hobby)parent.getItemAtPosition(position);
                deleteConfirm(hobby);

            }
        });

    }
    public void loadMyHobbies(){

        Runnable load=()-> {
            myhobbies.clear();
            try {
                Connection connection = new Connection();
                HttpURLConnection urlConnection = (HttpURLConnection) connection.connect("http://10.0.2.2:5000/user/me/", "GET");

                urlConnection.setConnectTimeout(2000);
                int responseCode = urlConnection.getResponseCode();


                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    JsonReader jsonReader = new JsonReader(in);

                    jsonReader.beginObject();

                    while (jsonReader.hasNext()) {
                        if (jsonReader.nextName().equals("hobbySet")) {
                            jsonReader.beginArray();
                            while (jsonReader.hasNext()) {
                                Hobby myhobby = new Hobby();
                                jsonReader.beginObject();
                                while (jsonReader.hasNext()) {
                                    String name = jsonReader.nextName();
                                    if (name.equals("name")) {
                                        myhobby.setName(jsonReader.nextString());
                                    } else if (name.equals("id")) {
                                        myhobby.setId(jsonReader.nextInt());
                                    } else {
                                        jsonReader.skipValue();
                                    }

                                }
                                jsonReader.endObject();
                                this.getActivity().runOnUiThread(()-> {
                                    myhobbies.add(myhobby);
                                    myhobbyadapter.notifyDataSetChanged();
                                });
                            }
                            jsonReader.endArray();
                        } else {
                            jsonReader.skipValue();
                        }


                    }
                    jsonReader.endObject();
                    in.close();
                } else {

                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s) {
                this.getActivity().runOnUiThread(()-> {
                    Toast.makeText(this.getActivity().getApplicationContext(),"No connection",Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {



            }
        };
            new Thread(load).start();
        }
    public void deleteConfirm(Hobby hobby){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this.getActivity());
        builder1.setMessage("Remove " + hobby.getName().toLowerCase() + " from your profile?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteHobby(hobby);
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



    public void deleteHobby(Hobby hobby){

        Runnable delete=()-> {
            myhobbies.clear();
            try {
                Connection connection = new Connection();
                HttpURLConnection urlConnection = (HttpURLConnection) connection.connect("http://10.0.2.2:5000/user/me/hobbies/"+hobby.getId(), "DELETE");

                urlConnection.setConnectTimeout(2000);
                int responseCode = urlConnection.getResponseCode();


                if (responseCode == HttpURLConnection.HTTP_OK) {
                    this.getActivity().runOnUiThread(()-> {
                        this.getActivity().findViewById(R.id.bottom_profile).performClick();
                        this.getActivity().runOnUiThread(()-> {
                            Toast.makeText(this.getActivity().getApplicationContext(),hobby.getName() + " deleted!", Toast.LENGTH_SHORT).show();
                        });
                    });

                } else {
                    this.getActivity().runOnUiThread(()-> {
                        Toast.makeText(this.getActivity().getApplicationContext(),"Something went wrong, try again later",Toast.LENGTH_SHORT).show();
                    });
                }
                urlConnection.disconnect();
            } catch (SocketTimeoutException s) {
                this.getActivity().runOnUiThread(()-> {
                    Toast.makeText(this.getActivity().getApplicationContext(),"No connection",Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {



            }
        };
        new Thread(delete).start();
    }

    }


