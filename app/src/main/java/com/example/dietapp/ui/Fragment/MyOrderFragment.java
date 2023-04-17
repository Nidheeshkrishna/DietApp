package com.example.dietapp.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.dietapp.ui.MainActivity;
import com.example.dietapp.ui.OrderAdapter;
import com.example.dietapp.ui.Productadapter;
import com.example.dietapp.ui.ProductsViewActivity;
import com.example.dietapp.ui.RetrofitClass;
import com.example.dietapp.ui.ShopAdapter;
import com.example.dietapp.ui.SignInActivity;
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

public class MyOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    RecyclerView rv_order;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    RecyclerView.Adapter ad;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyOrderFragment newInstance(String param1, String param2) {
        MyOrderFragment fragment = new MyOrderFragment();
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
        View  v = inflater.inflate(R.layout.fragment_my_order, container, false);
      //  return inflater.inflate(R.layout.fragment_my_order, container, false);

        rv_order=v.findViewById(R.id.orders_rv);
        rv_order.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        mSwipeRefreshLayout = v.findViewById(R.id.swipe_order);
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
                vieworders(SignInActivity.uid);
            }
        });

        return v;
    }

    private void vieworders(String uid) {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewoders(uid);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(getActivity(), "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean shop = new Bean();

                            shop.setPrname(ob.getString("name"));
                            shop.setShname(ob.getString("sh_name"));
                            shop.setPrimage(ob.getString("image"));
                            shop.setStatus(ob.getString("wrk_stat"));
                            shop.setPrprice(ob.getString("amount"));
                            shop.setShphone(ob.getString("phone"));


                            arr.add(shop);
                            ad = new OrderAdapter(arr, getActivity());
                            rv_order.setHasFixedSize(true);
                            lm = new LinearLayoutManager(getActivity());
                            rv_order.setLayoutManager(lm);
                            rv_order.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            // Stopping swipe refresh
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
        vieworders(SignInActivity.uid);
    }
}