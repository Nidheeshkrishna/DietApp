package com.example.dietapp.ui.Fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;
import com.example.dietapp.ui.constant;

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

       // ((VideosAdpater.Holder0) holder).tname.setText(arr.get(position).);
//        ((VideosAdpater.Holder0) holder).tphone.setText(b1.getTphone());
//        ((VideosAdpater.Holder0) holder).tcenter.setText(b1.getTcenter());
        VideoView simpleVideoView = ((Holder0) holder).simpleVideoView;
        MediaController mediaControls = new MediaController(c);
        mediaControls.setAnchorView(simpleVideoView);

simpleVideoView.setMediaController(mediaControls);

        Uri video = Uri.parse(arr.get(position).getVideoUrl());
        simpleVideoView.setVideoURI(video);
        simpleVideoView.start();



        simpleVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.setLooping(true);

                simpleVideoView.start();


            }
        });
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
        VideoView simpleVideoView;
        public Holder0(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.trainer_card);
           simpleVideoView = (VideoView) itemView.findViewById(R.id.simpleVideoView);
//
//            Uri uri = Uri.parse("https://images-assets.nasa.gov/video/KSC-20230417-MH-CSH01_0001-Cucumbers_Melons_PPA-3328058/KSC-20230417-MH-CSH01_0001-Cucumbers_Melons_PPA-3328058~medium.mp4");
//          // initiate a video view
//            simpleVideoView.setVideoURI(uri);
//            simpleVideoView.start();
//            tname = (TextView) itemView.findViewById(R.id.trainer_name);
//            tphone = (TextView) itemView.findViewById(R.id.tphone);
//            tcenter = (TextView) itemView.findViewById(R.id.text_center);


        }
    }

}
