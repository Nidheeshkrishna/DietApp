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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;

    public ShopAdapter(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id,number,phone,prname, prdesc, primage,prprice,shpid;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_layout, parent, false);
        return new ShopAdapter.Holder0(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);
        phone = b1.getShphone();


        ((ShopAdapter.Holder0) holder).shname.setText(b1.getShname());
        ((ShopAdapter.Holder0) holder).shaddr.setText(b1.getShaddress());
        ((ShopAdapter.Holder0) holder).shphone.setText(b1.getShphone());
        ((Holder0) holder).shphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                phone = b1.getShphone();
                Intent obj = new Intent(Intent.ACTION_DIAL);
                obj.setData(Uri.parse("tel:" + phone));
                c.startActivity(obj);
            }
        });

        ((Holder0) holder).cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);

                shpid=b1.getShid();
                /*id=b1.getPrid();
                prname = b1.getPrname();
                prprice = b1.getPrprice();
                prdesc=b1.getPrdesc();
                primage = b1.getPrimage();*/
                c.startActivity(new Intent(c,ProductsViewActivity.class));
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
        TextView shname,shaddr,shphone;

        public Holder0(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.shop_card);
            shname = (TextView) itemView.findViewById(R.id.shop_name);
            shaddr = (TextView) itemView.findViewById(R.id.shop_address);
            shphone = (TextView) itemView.findViewById(R.id.shop_phone);

        }
    }
}
