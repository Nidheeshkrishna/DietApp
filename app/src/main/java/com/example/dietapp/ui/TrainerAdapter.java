package com.example.dietapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;

import java.util.ArrayList;

public class TrainerAdapter extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;
    public TrainerAdapter(ArrayList<Bean> arr, TrainersListActivity trainersListActivity) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainers_layout, parent, false);
        return new Holder0(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);

        ((Holder0) holder).tname.setText(b1.getTname());
        ((Holder0) holder).temail.setText(b1.getTemail());
        ((Holder0) holder).tphone.setText(b1.getTphone());
        ((Holder0) holder).tcenter.setText(b1.getTcenter());

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

        CardView cardview;
        TextView tname,temail,tphone,tcenter;

        public Holder0(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.trainer_card);
            tname = (TextView) itemView.findViewById(R.id.trainer_name);
            temail = (TextView) itemView.findViewById(R.id.email);
            tphone = (TextView) itemView.findViewById(R.id.tphone);
            tcenter = (TextView) itemView.findViewById(R.id.text_center);

        }
    }
}
