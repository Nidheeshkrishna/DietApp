package com.example.dietapp.ui.Fragment;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;
import com.example.dietapp.ui.User_Trainees;

import java.util.ArrayList;

public class VideosAdpater extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;

    public VideosAdpater(ArrayList<Bean> arr, Context c) {

        this.arr = arr;
        this.c = c;
    }
    public int lastPosition = -2;
    public static String id,phone;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_layout, parent, false);
        return new VideosAdpater.Holder0(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);

       // ((VideosAdpater.Holder0) holder).tname.setText(b1.getTname());
//        ((VideosAdpater.Holder0) holder).tphone.setText(b1.getTphone());
//        ((VideosAdpater.Holder0) holder).tcenter.setText(b1.getTcenter());


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
//            tname = (TextView) itemView.findViewById(R.id.trainer_name);
//            tphone = (TextView) itemView.findViewById(R.id.tphone);
//            tcenter = (TextView) itemView.findViewById(R.id.text_center);

        }
    }

}
