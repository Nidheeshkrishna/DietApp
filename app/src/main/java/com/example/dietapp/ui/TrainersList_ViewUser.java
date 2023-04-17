package com.example.dietapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

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

public class TrainersList_ViewUser extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.Adapter ad;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_list__view_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        recyclerView = findViewById(R.id.rcv_trainers);

        recyclerView.setLayoutManager(new LinearLayoutManager(TrainersList_ViewUser.this,
                LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_trainers_user);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                viewtrainer_user();
            }
        });

    }

    private void viewtrainer_user() {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewtrainer();
        mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(TrainersList_ViewUser.this, "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean Allshops = new Bean();
                            Allshops.setTid(ob.getString("tid"));
                            Allshops.setTname(ob.getString("name"));
                            Allshops.setTphone(ob.getString("phone"));
                            Allshops.setTcenter(ob.getString("tcenter"));
                            Allshops.setTemail(ob.getString("email"));
                            Allshops.setTrstatus(ob.getString("status"));



                            arr.add(Allshops);
                            ad = new User_Trainees(arr, TrainersList_ViewUser.this);
                            recyclerView.setHasFixedSize(true);
                            lm = new LinearLayoutManager(TrainersList_ViewUser.this);
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            // Stopping swipe refresh
                            mSwipeRefreshLayout.setRefreshing(false);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    @Override
    public void onRefresh() {

    }
}