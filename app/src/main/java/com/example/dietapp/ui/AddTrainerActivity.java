package com.example.dietapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTrainerActivity extends AppCompatActivity {

    EditText edt_name,edt_center,edt_email,edt_phone,edt_pass;
    Button add_trainee;
    String name,center,phone,email,pass;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trainer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        edt_name=(EditText)findViewById(R.id.edt_trnrname);
        edt_center=(EditText)findViewById(R.id.edt_cntrname);
        edt_phone=(EditText)findViewById(R.id.edt_trnrpno);
        edt_email=(EditText)findViewById(R.id.edt_trnremid);
        edt_pass=(EditText)findViewById(R.id.edt_trnrpwd);
        add_trainee=(Button)findViewById(R.id.btn_addtrnr);

        add_trainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edt_name.getText().toString();
                center=edt_center.getText().toString();
                phone=edt_phone.getText().toString();
                email=edt_email.getText().toString();
                pass=edt_pass.getText().toString();

                if (name.isEmpty() || name.length() < 3|| !name.matches("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$")) {
                    edt_name.setError("enter your name(at least 3 characters)");
                    valid = false;
                }
                else if (center.isEmpty() ) {
                    edt_center.setError("Enter Training center");
                    valid = false;
                }
                else if (phone.isEmpty() || phone.length() < 10 | phone.length() > 10) {
                    edt_phone.setError("Enter Phone Number");
                    valid = false;
                }
                else if (email.isEmpty()|| !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ) {
                    edt_email.setError("Enter Valid Email");
                    valid = false;
                }
                else if (pass.isEmpty() ) {
                    edt_pass.setError("Enter  Password");
                    valid = false;
                }
                else {
                    fun(SignInActivity.uid,name,center,phone,email,pass);
                }

            }
        });

    }

    private void fun(String uid,String name, String center, String phone, String email, String pass) {

        final ProgressDialog progressDialog = new ProgressDialog(AddTrainerActivity.this);
        progressDialog.setMessage("please Wait...");
        progressDialog.show();
        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.Add_Service(uid,name,center,phone,email,pass);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {

                        String out = response.body().string();
                        if (out.equals("success")) {
                            Toast.makeText(AddTrainerActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddTrainerActivity.this,MainActivity.class));
                        }
                        else
                        {

                            Toast.makeText(AddTrainerActivity.this, " failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {

                    Toast.makeText(AddTrainerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(AddTrainerActivity.this, "Network error", Toast.LENGTH_SHORT).show();
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