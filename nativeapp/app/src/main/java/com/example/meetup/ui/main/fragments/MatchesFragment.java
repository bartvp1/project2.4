package com.example.meetup.ui.main.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meetup.R;
import com.example.meetup.ui.main.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment {

    //matchesview
    String matchnames[], matchphonenumbers[], matchhobbies[];
    RecyclerView recyclerview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            //initmatches recyclerview
            matchnames = getResources().getStringArray(R.array.matches_names);
            matchphonenumbers = getResources().getStringArray(R.array.matches_phonenumbers);
            matchhobbies = getResources().getStringArray(R.array.matches_hobbies);
            recyclerview = this.getActivity().findViewById(R.id.matchesrecyclerview);




            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), matchnames, matchphonenumbers, matchhobbies);
            recyclerview.setAdapter(recyclerViewAdapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this.getContext()));


        }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_matches, container, false);
        return root;
    }
}
