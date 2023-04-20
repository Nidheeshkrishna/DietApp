package com.example.dietapp.ui.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.Connection.Bean;
import com.example.dietapp.ui.RetrofitClass;
import com.example.dietapp.ui.SignInActivity;
import com.example.dietapp.ui.TrainerAdapter;
import com.example.dietapp.ui.User_Trainees;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.Adapter ad;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    Spinner spin_weight;

    public UserVideoFragment() {
        // Required empty public constructor
    }

    public static UserVideoFragment newInstance(String param1, String param2) {
        UserVideoFragment fragment = new UserVideoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_video, container, false);
        recyclerView = view.findViewById(R.id.rcv_videos);
        spin_weight = view.findViewById(R.id.spinner_weight);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        view_weight();
        viewvideos(SignInActivity.uid);

//        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_trainers_user);
//
//
//       mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setRefreshing(true);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);
//        mSwipeRefreshLayout.post(() -> {
//            mSwipeRefreshLayout.setRefreshing(true);
//            // Fetching data from server
//            viewvideos(SignInActivity.uid);
//
//        });




        return view;
    }

    public void view_weight() {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.view_weight();
       // mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                 //   mSwipeRefreshLayout.setRefreshing(false);
                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(getContext(), "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean Allshops = new Bean();

                            arr.add(Allshops);
                            ad = new VideosAdpater(arr, getActivity());
                            recyclerView.setHasFixedSize(true);
                            lm = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            // Stopping swipe refresh
                          //  mSwipeRefreshLayout.setRefreshing(false);

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

    private void viewvideos(String uid) {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewvideos();
       // mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                   // mSwipeRefreshLayout.setRefreshing(false);
                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(getContext(), "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean Allshops = new Bean();
                            // Allshops.setTid(ob.getString("tid"));
                            Allshops.setTname(ob.getString("video"));
                            Allshops.setTphone(ob.getString("day"));
                            Allshops.setTcenter(ob.getString("des"));

//                            Allshops.setTname("video");
//                            Allshops.setTphone("phone");
//                            Allshops.setTcenter("tcenter");
                            arr.add(Allshops);
                            ad = new VideosAdpater(arr, getActivity());
                            recyclerView.setHasFixedSize(true);
                            lm = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            // Stopping swipe refresh
                            //mSwipeRefreshLayout.setRefreshing(false);

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