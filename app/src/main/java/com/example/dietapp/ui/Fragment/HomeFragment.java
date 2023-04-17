package com.example.dietapp.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.AddBankActivity;
import com.example.dietapp.ui.AddTrainerActivity;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.RetrofitClass;
import com.example.dietapp.ui.ShopListActivity;
import com.example.dietapp.ui.SignInActivity;
import com.example.dietapp.ui.TrainersListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.dietapp.ui.SignInActivity.status;


public class HomeFragment extends Fragment {

    CardView viewshop,viewtrnrs,addtrnrs,addbnk;


    public HomeFragment() {

    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View  v = inflater.inflate(R.layout.fragment_home, container, false);

        viewshop = v.findViewById(R.id.cv_viewshop);
        viewtrnrs = v.findViewById(R.id.cv_viewtrnrs);
        addbnk = v.findViewById(R.id.cv_addbnk);
        addtrnrs = v.findViewById(R.id.cv_addtrnrs);


        addbnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getbank_status(SignInActivity.uid);
            }
        });

        addtrnrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddTrainerActivity.class);
                startActivity(intent);
            }
        });

        viewtrnrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TrainersListActivity.class);
                startActivity(intent);
            }
        });
        viewshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShopListActivity.class);
                startActivity(intent);
            }
        });
     return v;
    }

    private void getbank_status(String uid) {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.Bankstatus(uid);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try{
                        String out =response.body().string().trim();
                        if(out.equals("null")){
                            startActivity(new Intent(getContext(), AddBankActivity.class));
                        }
                        else {
                        JSONArray jsonarray = new JSONArray(out);
                        for (int i = 0; i <jsonarray.length() ; i++) {
                            JSONObject ob = jsonarray.getJSONObject(i);
                            status = ob.getString("status");
                            if(status.equals("verified")) {
                                Toast.makeText(getContext(), "Already Linked..!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        }
                    }
                    catch (IOException e)
                    {
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
}