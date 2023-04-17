package com.example.dietapp.ui;

import androidx.appcompat.app.AppCompatActivity;
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

public class ProductsViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    RecyclerView rv_products;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static ArrayList<Bean> arr;
    RecyclerView.LayoutManager lm;
    Bean b;
    RecyclerView.Adapter ad;
    private TrainerAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_view);

        rv_products=(RecyclerView)findViewById(R.id.products_rv);
        rv_products.setLayoutManager(new LinearLayoutManager(ProductsViewActivity.this, LinearLayoutManager.VERTICAL, false));
        arr = new ArrayList<Bean>();
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_products);
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
                viewproducts(ShopAdapter.shpid);
            }
        });

    }

    private void viewproducts(String shopid) {
        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.viewproducts(shopid);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    try {
                        String out = response.body().string().trim();
                        if (out.equals("null")) {
                            Toast.makeText(ProductsViewActivity.this, "No Records", Toast.LENGTH_SHORT).show();
                        }
                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean shop = new Bean();

                            shop.setPrid(ob.getString("shid"));
                            shop.setPrid(ob.getString("pid"));
                            shop.setPrname(ob.getString("name"));
                            shop.setPrdesc(ob.getString("description"));
                            shop.setPrprice(ob.getString("price"));
                            shop.setPrimage(ob.getString("image"));

                            arr.add(shop);
                            ad = new Productadapter(arr, ProductsViewActivity.this);
                            rv_products.setHasFixedSize(true);
                            lm = new LinearLayoutManager(ProductsViewActivity.this);
                            rv_products.setLayoutManager(lm);
                            rv_products.setAdapter(ad);
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
        mSwipeRefreshLayout.setRefreshing(true);
        viewproducts(ShopAdapter.shpid);
    }
}