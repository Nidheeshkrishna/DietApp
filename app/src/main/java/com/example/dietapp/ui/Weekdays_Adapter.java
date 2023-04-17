/*
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Weekdays_Adapter extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;


    public Weekdays_Adapter(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String contact, vehusid, vehusname,routee, imagee, driverusname, vehusno, usdrivercon, usseats, usminamount, usvtype, usvehmodel,routeid;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_layout, parent, false);
        return new Weekdays_Adapter.Holder0(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);
        String img = b1.getImagee();
        contact = b1.getCon();

        ((Weekdays_Adapter.Holder0) holder).vehname.setText("Vehicle Name: "+b1.getVehname());
        ((Weekdays_Adapter.Holder0) holder).drivername.setText("Driver: "+b1.getDrivername()+"("+b1.getDriver_Contact()+")");
        ((Weekdays_Adapter.Holder0) holder).vehno.setText("Veh No: "+b1.getVehno());
        ((Weekdays_Adapter.Holder0) holder).route.setText(b1.getRoute());
        ((Weekdays_Adapter.Holder0) holder).type.setText("Veh type:"+b1.getVehitype());
        ((Holder0) holder).type.setTextColor(Color.RED);


        Picasso.with(c).load(constant.url + img).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(((Holder0) holder).im);
        ((Weekdays_Adapter.Holder0) holder). drivername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                contact = b1.getDriver_Contact();
                Intent obj = new Intent(Intent.ACTION_DIAL);
                obj.setData(Uri.parse("tel:" + contact));
                c.startActivity(obj);
            }
        });

        setAnimation(holder.itemView, position);

        ((Holder0) holder).textmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                vehusid = b1.getVehid();
                vehusname = b1.getVehname();
                routee = b1.getRoute();
                routeid=b1.getRoute_id();
                imagee = b1.getImagee();
                driverusname = b1.getDriver();
                vehusno = b1.getVehinum();
                usdrivercon = b1.getCon();
                usseats = b1.getSeats();
                usminamount = b1.getMinamount();
                usvtype = b1.getVtype();
                usvehmodel = b1.getVehimodel();
                c.startActivity(new Intent(c,View_VechicleBooking_Activity.class));
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        TextView dayname,drivername, route, vehno ,type;
        Button textmore;
        ImageView im;

        public Holder0(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.cv_userveh);
            dayname = (TextView) itemView.findViewById(R.id.dayname);
            drivername=(TextView)itemView.findViewById(R.id.driver);
            type=(TextView)itemView.findViewById(R.id.type);
            vehno = (TextView) itemView.findViewById(R.id.vehnum);
            textmore = (Button) itemView.findViewById(R.id.text_view_usermore);
            route = (TextView) itemView.findViewById(R.id.route_usercard);
            im = (ImageView) itemView.findViewById(R.id.ima);
        }
    }

}
*/
