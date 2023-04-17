package com.example.dietapp.ui.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;
import com.example.dietapp.ui.RetrofitClass;
import com.example.dietapp.ui.SignInActivity;
import com.example.dietapp.ui.WelcomePageActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class UserProfileFragment extends Fragment {

    TextView usrname,usremid,usrpno;
    Button logout;
    public static String id;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
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

        final View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        view_profile(SignInActivity.uid);
        usrname = view.findViewById(R.id.txtv_usrname);
        usremid = view.findViewById(R.id.txtv_emid);
        usrpno = view.findViewById(R.id.txtv_pno);
        logout = view.findViewById(R.id.btn_usrlout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("LogOut");
                alert.setMessage("Are you sure you want to Logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity().getApplication(), SignInActivity.class);
                        startActivity(intent);
                        SharedPreferences sp = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        dialogInterface.dismiss();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
            }
        });


        return view;
    }

    private void view_profile(String uid) {

        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.profile(uid);
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
                            String username = ob.getString("name");
                            usrname.setText(""+username);
                            String email = ob.getString("email");
                            usremid.setText(""+email);
                            id = ob.getString("id");
                            String userphone = ob.getString("phone");
                            usrpno.setText(""+userphone);
                        }
                    }
                    catch (IOException e) {
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