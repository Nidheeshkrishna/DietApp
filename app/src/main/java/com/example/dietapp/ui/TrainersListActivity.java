package com.example.dietapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
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

public class TrainersListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

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
        setContentView(R.layout.activity_trainers_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        recyclerView = findViewById(R.id.rcv_trnrs);

        recyclerView.setLayoutManager(new LinearLayoutManager(TrainersListActivity.this,
                LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_trainers);
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
                viewtrainers(SignInActivity.uid);
            }
        });
    }

    private void viewtrainers(String uid) {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewtrainers(uid);
        mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(TrainersListActivity.this, "No Records", Toast.LENGTH_SHORT).show();
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

                            arr.add(Allshops);
                            ad = new TrainerAdapter(arr, TrainersListActivity.this);
                            recyclerView.setHasFixedSize(true);
                            lm = new LinearLayoutManager(TrainersListActivity.this);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {

    }
}