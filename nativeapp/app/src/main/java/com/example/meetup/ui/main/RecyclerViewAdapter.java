package com.example.meetup.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetup.R;
import com.example.meetup.ui.main.fragments.MatchesFragment;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final ArrayList<MatchesFragment.Match> matches;
    private final Context context;


    public RecyclerViewAdapter(Context ct, ArrayList<MatchesFragment.Match> arr){
        this.context = ct;
        this.matches = arr;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.match_container, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(matches.get(position).getName());
        holder.city.setText(matches.get(position).getCity());
        holder.phonenumber.setText(matches.get(position).getPhonenumber());
        holder.hobbies.setText(matches.get(position).getHobbies().toString());
        holder.samehobbies.setText(matches.get(position).getSameHobbies().toString());
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, city, phonenumber, hobbies, samehobbies;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.matchname);
            city = itemView.findViewById(R.id.matchcity);
            phonenumber = itemView.findViewById(R.id.matchphonenumber);
            hobbies = itemView.findViewById(R.id.matchhobbies);
            samehobbies = itemView.findViewById(R.id.matchsamehobbies);
        }
    }
}
