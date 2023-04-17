package com.example.dietapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText edtname,edtemail,edtpno,edtpwd1,edtre_pwd,edt_address,edt_district;
    Button btn_signup;
    ApInterface api;
    String name,email,phone,password,re_pwd,address,district;
    Context mContext;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtname = findViewById(R.id.edt_name);
        edtemail = findViewById(R.id.edt_email);
        edtpno = findViewById(R.id.edt_phone);
        edtpwd1 = findViewById(R.id.edt_userpwd);
        edtre_pwd = findViewById(R.id.edt_repwd);
        edt_address = findViewById(R.id.edt_address);
        edt_district = findViewById(R.id.edt_district);

        btn_signup = findViewById(R.id.btn_usrsignup);
        progressDialog =new  ProgressDialog(this);
        api = RetrofitClass.getClient().create(ApInterface.class);
        mContext = this;

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = edtname.getText().toString();
                password = edtpwd1.getText().toString();
                re_pwd = edtre_pwd.getText().toString();
                phone = edtpno.getText().toString();
                email = edtemail.getText().toString();
                address=edt_address.getText().toString();
                district=edt_district.getText().toString();

                if (name == null){
                    Toast.makeText(mContext, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (email==null){
                    Toast.makeText(mContext, "Enter your email address", Toast.LENGTH_SHORT).show();
                }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(mContext, "Enter a Valid Email Address", Toast.LENGTH_SHORT).show();
                }else if (phone.length()!=10){
                    Toast.makeText(mContext, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                }else if (password.length()<4){
                    Toast.makeText(mContext, "Password must contain at least 4 characters", Toast.LENGTH_SHORT).show();
                }
                else if (address.isEmpty()){
                    Toast.makeText(mContext, "Enter valid address", Toast.LENGTH_SHORT).show();
                }
                else if (district.isEmpty()){
                    Toast.makeText(mContext, "Enter valid District", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(re_pwd)){
                    Toast.makeText(mContext, "Both passwords must be same", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.show();
                    Call<ResponseBody> call =api.usrregister(name,password,email,phone,address,district);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivityUser.class);
                            startActivity(intent);
                            finish();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Failed"+t, Toast.LENGTH_LONG).show();
                            Log.e("",t.toString());
                        }
                    });

                }


            }
        });
    }
}