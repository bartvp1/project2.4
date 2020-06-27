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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final String names[], phonenumbers[], hobbies[];
    private final Context context;


    public RecyclerViewAdapter(Context ct, String names[], String phonenumbers[], String hobbies[]){
        this.context = ct;
        this.names = names;
        this.phonenumbers = phonenumbers;
        this.hobbies = hobbies;
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
        holder.name.setText(names[position]);
        holder.phonenumber.setText(phonenumbers[position]);
        holder.hobbies.setText(hobbies[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, phonenumber, hobbies;
        ImageView avatar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.matchname);
            phonenumber = itemView.findViewById(R.id.matchphonenumber);
            hobbies = itemView.findViewById(R.id.matchhobbies);
        }
    }
}
