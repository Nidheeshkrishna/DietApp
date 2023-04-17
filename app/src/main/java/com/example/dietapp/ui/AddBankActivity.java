package com.example.dietapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBankActivity extends AppCompatActivity {

    EditText edt_name,edt_branch,edt_acc,edt_ifsc;
    Button add_bank;
    Spinner bank;
    String name,branch,acc,ifsc,banks;
    boolean valid = true;
    ArrayList<String> typeid=new ArrayList<>();
    ArrayList<String> type=new ArrayList<>();
    public static String ids,types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);

        getbank();
        edt_name=(EditText)findViewById(R.id.bnk_name);
        edt_branch=(EditText)findViewById(R.id.edt_branch);
        edt_acc=(EditText)findViewById(R.id.edt_accno);
        edt_ifsc=(EditText)findViewById(R.id.edt_ifsc);
        bank=(Spinner)findViewById(R.id.bank);
        add_bank=(Button)findViewById(R.id.btn_addbnk);

        add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=edt_name.getText().toString();
                branch=edt_branch.getText().toString();
                acc=edt_acc.getText().toString();
                ifsc=edt_ifsc.getText().toString();

                if (name.equals("")) {
                    edt_name.setError("Enter name");
                    valid = false;
                }
                else  if (branch.equals("")) {
                    edt_branch.setError("Enter Branch");
                    valid = false;
                }
                else  if (acc.equals("")) {
                    edt_acc.setError("Enter Account number");
                    valid = false;
                }else  if (ifsc.equals("")) {
                    edt_ifsc.setError("Enter IFSC");
                    valid = false;
                }
                else if(types.equals("select bank type")){
                    Toast.makeText(AddBankActivity.this, "Select Vehicle type", Toast.LENGTH_SHORT).show();
                }
                else {
                    fun(SignInActivity.uid,name, branch,acc,ifsc,ids);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

    }

    private void fun(String uid,String name, String branch, String acc, String ifsc, String types) {

        final ProgressDialog pgs;
        pgs = new ProgressDialog(AddBankActivity.this);
        pgs.setMessage("Registering");
        pgs.show();
        ApInterface ap= RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.AddBank(uid,name,branch,acc,ifsc,types);
        ca.enqueue(new Callback<ResponseBody>()
        {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String output = response.body().string();
                        Toast.makeText(AddBankActivity.this, ""+output, Toast.LENGTH_SHORT).show();

                        if (output.equals("success")){
                            Toast.makeText(AddBankActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddBankActivity.this,MainActivity.class));
                        }
                    } catch (Exception e) {
                        Toast.makeText(AddBankActivity.this, "", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddBankActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getbank() {
        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca=ap.getbanks();
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
                            String bid = ob.getString("id");
                            typeid.add(bid);
                            String bname = ob.getString("name");
                            type.add(bname);
                        }

                        bank.setAdapter(new ArrayAdapter<>(AddBankActivity.this,android.R.layout.simple_spinner_dropdown_item,type));
                        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                ids=typeid.get(i);
                                types=type.get(i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
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
}