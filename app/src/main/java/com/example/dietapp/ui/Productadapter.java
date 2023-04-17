package com.example.dietapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.dietapp.ui.constant.url;

public class Productadapter extends RecyclerView.Adapter{

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;
    private ImageView pick;

    public Productadapter(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id,number,prname, prdesc, primage,prprice,shpid;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new Productadapter.Holder0(v);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);

        String img=b1.getPrimage();
        ((Productadapter.Holder0) holder).prname.setText(b1.getPrname());
        ((Productadapter.Holder0) holder).prdesc.setText(b1.getPrdesc());
        ((Productadapter.Holder0) holder).prprice.setText(b1.getPrprice()+"/-");
        ((Productadapter.Holder0) holder).prdesc.setText(b1.getPrdesc());
        ((Holder0) holder).prprice.setTextColor(Color.RED);

        Picasso.with(c).load(constant.url + img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(((Holder0) holder).im);
        ((Productadapter.Holder0) holder).book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);

                id=b1.getPrid();
                shpid=b1.getShid();
                prname = b1.getPrname();
                prdesc=b1.getPrdesc();
                prprice = b1.getPrprice();
                //primage=b1.getPrimage();
                c.startActivity(new Intent(c,Payment.class));
            }
        });

       /* ((ShopAdapter.Holder0) holder).shphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                number=b1.getShphone();
                Intent obj = new Intent(Intent.ACTION_DIAL);
                obj.setData(Uri.parse("tel:" + number));
                c.startActivity(obj);
            }
        });*/

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

        TextView prname,prdesc,prprice;
//        ImageView pdctimage;
ImageView im;
        Button book;

        public Holder0(View itemView) {
            super(itemView);
            im = (ImageView) itemView.findViewById(R.id.im);


            prname = (TextView) itemView.findViewById(R.id.pdt_name);
//            pdctimage=(ImageView)itemView.findViewById(R.id.pdt_images);
            prdesc = (TextView) itemView.findViewById(R.id.pr_desc);
            prprice = (TextView) itemView.findViewById(R.id.pr_price);
            book=(Button)itemView.findViewById(R.id.buy_button);

        }
    }
}
