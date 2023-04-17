package com.example.dietapp.ui.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActiveOrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActiveOrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rv;
    RecyclerView.Adapter ad;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    RecyclerView trainee_rv;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ActiveOrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActiveOrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActiveOrdersFragment newInstance(String param1, String param2) {
        ActiveOrdersFragment fragment = new ActiveOrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_active_orders, container, false); b = new Bean();
        arr = new ArrayList<Bean>();
        rv = (RecyclerView) rootView.findViewById(R.id.trainers_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_orders);
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
                //viewtrainers();

            }
        });

        trainee_rv = (RecyclerView) rootView.findViewById(R.id.trainers_view);
        layoutManager = new LinearLayoutManager(getActivity());
        trainee_rv.setLayoutManager(layoutManager);
        trainee_rv.setAdapter(mAdapter);

        return rootView;

       /* return inflater.inflate(R.layout.fragment_active_orders, container, false);
        trainee_rv=(RecyclerView)c.findViewById(R.id.trainers_view);*/

    }

    private void vieworders() {
      /*  ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.vieworders();
        mSwipeRefreshLayout.setRefreshing(true);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(ViewAllShops.this, "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean Allshops = new Bean();
                            Allshops.setShop_id(ob.getString("id"));
                            Allshops.setShop_name(ob.getString("s_name"));
                            Allshops.setShop_address(ob.getString("s_address"));
                            Allshops.setShop_contact(ob.getString("s_contact"));
                            Allshops.setShop_image(ob.getString("s_image"));
                            Allshops.setShop_lat(ob.getString("latitude"));
                            Allshops.setShop_lon(ob.getString("longitude"));
                            Allshops.setShop_opentime(ob.getString("o_time"));
                            Allshops.setShopclosetime(ob.getString("c_time"));
                            Allshops.setShop_holidays(ob.getString("holiday"));
                            arr.add(Allshops);
                            ad = new AllShop_Adapter(arr, ViewAllShops.this);
                            rv.setHasFixedSize(true);
                            lm = new LinearLayoutManager(ViewAllShops.this);
                            rv.setLayoutManager(lm);
                            rv.setAdapter(ad);
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
        });*/
    }

    @Override
    public void onRefresh() {
            //viewtrainers();
    }
}