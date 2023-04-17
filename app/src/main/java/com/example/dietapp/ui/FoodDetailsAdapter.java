package com.example.dietapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.dietapp.ui.constant.url;

public class FoodDetailsAdapter extends RecyclerView.Adapter{


    ArrayList<Bean> arr = new ArrayList<>();
    Context c;
    private ImageView pick;

    public FoodDetailsAdapter(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id,number,prname, prdesc, primage,prprice,shpid;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_layout, parent, false);
        return new FoodDetailsAdapter.Holder0(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);


       // ((FoodDetailsAdapter.Holder0) holder).day.setText(b1.getId());
        ((Holder0) holder).breakfast.setText("Breakfast  : "+b1.getBreakfast());
        ((Holder0) holder).dinner.setText("Dinner      : "+b1.getDinner());
        ((Holder0) holder).lunch.setText("Lunch        : "+b1.getLunch());
        ((Holder0) holder).day.setText(b1.getDays());
        ((Holder0) holder).status.setText("Weight: "+b1.getWeight());

        setAnimation(holder.itemView, position);
    }
    private void setAnimation(View itemView, int position) {

        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            lastPosition = position;
            Animation am = new TranslateAnimation(-700.0f, 0.0f, 0.0f, 0.0f);
            am.setDuration(1000);
            am.setFillAfter(false);
            am.setInterpolator(new OvershootInterpolator());
            itemView.startAnimation(am);
        }

    }
    @Override
    public int getItemCount() {
        return arr.size();
    }

    private class Holder0 extends RecyclerView.ViewHolder {

        TextView day,breakfast,lunch,dinner,status;

        public Holder0(View itemView) {
            super(itemView);

            breakfast = (TextView) itemView.findViewById(R.id.breakfast);
            day=(TextView)itemView.findViewById(R.id.dayname);
            lunch = (TextView) itemView.findViewById(R.id.lunch);
            status = (TextView) itemView.findViewById(R.id.status);
            dinner = (TextView) itemView.findViewById(R.id.dinner);
        }
    }
}
