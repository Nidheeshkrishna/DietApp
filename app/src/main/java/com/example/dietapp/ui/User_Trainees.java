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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.Connection.Bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User_Trainees extends RecyclerView.Adapter {

    ArrayList<Bean> arr = new ArrayList<>();
    Context c;
    public User_Trainees(ArrayList<Bean> arr, Context c) {
        this.arr = arr;
        this.c = c;
    }

    public int lastPosition = -2;
    public static String id,phone,trainerid,userid;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trainee_layout, parent, false);
        return new User_Trainees.Holder0(v);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        Bean b1 = new Bean();
        b1 = arr.get(position);

        String status=b1.getTrstatus();
        ((User_Trainees.Holder0) holder).tid.setText(b1.getTid());
        ((User_Trainees.Holder0) holder).tname.setText(b1.getTname());
        ((User_Trainees.Holder0) holder).tphone.setText(b1.getTphone());
        ((User_Trainees.Holder0) holder).tcenter.setText(b1.getTcenter());
       // ((User_Trainees.Holder0) holder).rqst.setText(b1.getTrstatus());

        ((Holder0) holder).tphone.setTextColor(Color.parseColor("#5974AB"));

       /* ((Holder0) holder).tphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                phone = b1.getShphone();
                Intent obj = new Intent(Intent.ACTION_DIAL);
                obj.setData(Uri.parse("tel:" + phone));
                c.startActivity(obj);
            }
        });*/
       /*if (status.equals("requested"))
       {
           ((Holder0) holder).request.setVisibility(View.GONE);
           ((Holder0) holder).rqst.setVisibility(View.VISIBLE);
       }

        if (status.equals("null"))
        {
            ((Holder0) holder).request.setVisibility(View.VISIBLE);
            ((Holder0) holder).rqst.setVisibility(View.GONE);
        }
        if (status.equals("completed"))
        {
            ((Holder0) holder).request.setVisibility(View.VISIBLE);
            ((Holder0) holder).rqst.setVisibility(View.GONE);
        }
*/
        ((Holder0) holder).request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bean b1 = new Bean();
                b1 = arr.get(position);
                trainerid=b1.getTid();
                request(SignInActivity.uid,User_Trainees.trainerid);
                /*((Holder0) holder).rqst.setVisibility(View.VISIBLE);
                ((Holder0) holder).request.setVisibility(View.GONE);*/

            }
        });

        setAnimation(holder.itemView, position);
    }

    private void request(String uid, String trainerid) {

        ApInterface ap= RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.user_request(uid,trainerid);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String  out = response.body().string();
                        if (out.equals("success")){
/*                            ((Holder0) holder).rqst.setVisibility(View.VISIBLE);
                            ((Holder0) holder).request.setVisibility(View.GONE);*/

                            Toast.makeText(c, "Requested", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(c, "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
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
        TextView tid,tname,rqst,tphone,tcenter;
        Button request;

        public Holder0(View itemView) {
            super(itemView);
            cardview = (CardView) itemView.findViewById(R.id.trainer_card);
            tid = (TextView) itemView.findViewById(R.id.trainer_id);
            tname = (TextView) itemView.findViewById(R.id.trainer_name);
            tphone = (TextView) itemView.findViewById(R.id.tphone);
            rqst = (TextView) itemView.findViewById(R.id.text_rqstd);
            request=(Button)itemView.findViewById(R.id.btn_rqst);
            tcenter = (TextView) itemView.findViewById(R.id.text_center);
        }
    }
}