package com.example.dietapp.ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.Bean;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeekDaysAdapter extends RecyclerView.Adapter<WeekDaysAdapter.Holder> {

    String[] weekdays={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public static String week;

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;

    public WeekDaysAdapter() {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String Shop_ID,shop_image,shop_name,shop_address,shop_email,shop_contact,shop_des,shop_lat,shop_lon,shop_time,shop_holi;



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekdays_layout,parent,false);
        WeekDaysAdapter.Holder holder =new WeekDaysAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {

        holder.days.setText(weekdays[position]);

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //trying to get ID From card
                ViewGroup parentView = (ViewGroup)v.getParent();
                TextView idpatientview =(TextView)parentView.findViewById(R.id.);
                week = idpatientview.getText().toString();
                PreferencesHelper.save(c, "idPatient", week);
                Intent intent = new Intent(c, WeekDaysAdapter.class);
                c.startActivity(intent);

            }
        });*/
        holder.cv_weekdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Toast.makeText(holder.cv.getContext(), "Clicked card: "+(holder.titleView.getText()), Toast.LENGTH_SHORT).show();
                Context context = v.getContext();*/

                Toast.makeText(holder.mContext, ""+weekdays[position], Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return weekdays.length;
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView days,dayid;
        CardView cv_weekdays;
        Context mContext;
        
        public Holder(@NonNull View itemView) {
            
            super(itemView);
            
            dayid =itemView.findViewById(R.id.id_day);
            days =itemView.findViewById(R.id.txtv_day);
            cv_weekdays = itemView.findViewById(R.id.cv_weedays);
            mContext = itemView.getContext();

        }
    }
}
