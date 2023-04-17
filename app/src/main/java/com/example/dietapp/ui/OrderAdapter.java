package com.example.dietapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;

    public OrderAdapter(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id,number,phone;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
        return new OrderAdapter.Holder0(v);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        Bean b1 = new Bean();
        b1 = arr.get(position);
        String img=b1.getPrimage();

        ((OrderAdapter.Holder0) holder).odrname.setText(b1.getPrname());
        ((OrderAdapter.Holder0) holder).odrprice.setText(b1.getPrprice()+"/-");
//        ((OrderAdapter.Holder0) holder).odrqty.setText(b1.getShname());
        ((OrderAdapter.Holder0) holder).ordstatus.setText(b1.getStatus());
        ((OrderAdapter.Holder0) holder).ordershname.setText(b1.getShname());
        ((OrderAdapter.Holder0) holder).ordphone.setText(b1.getShphone());
        ((OrderAdapter.Holder0) holder).ordphone.setOnClickListener(new View.OnClickListener() {
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
        Picasso.with(c).load(constant.url + img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(((OrderAdapter.Holder0) holder).pdtimage);

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

        TextView odrname,odrprice,ordstatus,ordershname,ordphone;
        ImageView pdtimage;


        public Holder0(View itemView) {
            super(itemView);
            ordershname= (TextView) itemView.findViewById(R.id.tv_shopname);
            odrname = (TextView) itemView.findViewById(R.id.pdt_order_name);
            pdtimage=(ImageView)itemView.findViewById(R.id.product_image);
           // odrqty = (TextView) itemView.findViewById(R.id.tv_qty);
            odrprice = (TextView) itemView.findViewById(R.id.tv_price);
            ordphone = (TextView) itemView.findViewById(R.id.tv_phone);
            ordstatus = (TextView) itemView.findViewById(R.id.orderstatus);

        }
    }
}
