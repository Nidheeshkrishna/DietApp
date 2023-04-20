package com.example.dietapp.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.Connection.Bean;
import com.example.dietapp.ui.RetrofitClass;
import com.example.dietapp.ui.TrainerAdapter;
import com.example.dietapp.ui.TrainersList_ViewUser;
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

public class UsrTrnrsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView.Adapter ad;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    public UsrTrnrsListFragment() {
        // Required empty public constructor
    }

    public static UsrTrnrsListFragment newInstance(String param1, String param2) {
        UsrTrnrsListFragment fragment = new UsrTrnrsListFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_usr_trnrs_list, container, false);
        recyclerView = view.findViewById(R.id.rcv_trainers);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_trainers_user);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                // Fetching data from server
                viewtrainer_user();
            }
        });

        return view;
    }

    private void viewtrainer_user() {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewtrainee();
        mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mSwipeRefreshLayout.setRefreshing(false);
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

                            Allshops.setTid(ob.getString("tid"));
                            Allshops.setTname(ob.getString("name"));
                            Allshops.setTphone(ob.getString("phone"));
                            Allshops.setTcenter(ob.getString("tcenter"));
                         //   Allshops.setTrstatus(ob.getString("status"));

/*

                            arr.add(Allshops);
                            ad = new User_Trainees(arr, getActivity());
                            recyclerView.setHasFixedSize(true);
                            lm=new GridLayoutManager(getActivity(), 2);
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(ad);
                            ad.notifyDataSetChanged();
*/


                            arr.add(Allshops);

                            ad = new User_Trainees(arr, getActivity());
                            recyclerView.setHasFixedSize(true);
                            lm = new LinearLayoutManager(getActivity());
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