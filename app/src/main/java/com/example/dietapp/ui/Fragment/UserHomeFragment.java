package com.example.dietapp.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Adapter.WeekDaysAdapter;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.Connection.Bean;
import com.example.dietapp.ui.FoodDetailsAdapter;
import com.example.dietapp.ui.RetrofitClass;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeFragment extends Fragment {

    RecyclerView recyclerView;
    WeekDaysAdapter adapter;
    Spinner spin_weight;
    ArrayList<String> typename=new ArrayList<>();
    ArrayList<String> typeid=new ArrayList<>();
    Bean b;
    RecyclerView.Adapter ad;
    public static ArrayList<Bean> arr;
    public static String status,tids,tnms;
    public static String dayid,dayname;
    RecyclerView.LayoutManager lm;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    public static UserHomeFragment newInstance(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
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

        View view = inflater.inflate(R.layout.fragment_user_home, container, false);

        b = new Bean();
        arr = new ArrayList<Bean>();
        recyclerView = view.findViewById(R.id.rcv_weekdays);
        spin_weight=view.findViewById(R.id.spinner_weight);
        //adapter = new Weekdays_Adapter();

        getweight();
        //getfooddetails();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getweight() {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.getweight();
        ca.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try{
                        String out =response.body().string();
                        JSONArray jsonarray = new JSONArray(out);
                        for (int i = 0; i <jsonarray.length() ; i++) {
                            JSONObject ob = jsonarray.getJSONObject(i);
                            String daysid = ob.getString("wid");
                            typeid.add(daysid);
                            String daynames = ob.getString("category");
                            typename.add(daynames);

                        }
                        spin_weight.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item ,typename));
                        spin_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                dayid = typeid.get(i);
                                dayname= typename.get(i);
                                getdetails(dayid);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }
                    catch (IOException e) { e.printStackTrace(); }
                    catch (JSONException e) { e.printStackTrace(); }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });

    }

    private void getdetails(String tids) {

        ApInterface ap= RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.getdetailss(tids);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try {
                        String  out = response.body().string();
                        //Toast.makeText( getContext(), ""+out, Toast.LENGTH_SHORT).show();

                        JSONArray jar = new JSONArray(out);
                        arr.clear();
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject ob = jar.getJSONObject(i);
                            Bean requests = new Bean();
                        //    requests.setId(ob.getString("fid"));
                            requests.setBreakfast(ob.getString("breakfast"));
                            requests.setLunch(ob.getString("lunch"));
                            requests.setDinner(ob.getString("dinner"));
                            requests.setDays(ob.getString("day"));
                            requests.setWeight(ob.getString("category"));

                            arr.add(requests);
                            ad = new FoodDetailsAdapter(arr,getContext());
                            recyclerView.setHasFixedSize(true);
                             lm = new LinearLayoutManager(getContext());
//                            mLayoutManager=new GridLayoutManager(UserView_privateveh.this, 2);
//                            recview.setLayoutManager(mLayoutManager);
                            lm = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(lm);
                            recyclerView.setAdapter(ad);
                            ad.notifyDataSetChanged();
                            // Stopping swipe refresh
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                { Toast.makeText(getContext(), "No user found", Toast.LENGTH_SHORT).show(); }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
