package com.example.dietapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dietapp.R;
import com.example.dietapp.ui.Connection.ApInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    EditText edt_uname,edt_pwd;
    Button btn_signin;
    String email,password;
    public static String id, role, status, user_name, value, username, uid, image, type;
    SharedPreferences sp;
    boolean valid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setTitle("DietApp");
        edt_uname = (EditText) findViewById(R.id.edt_username);
        edt_pwd = (EditText) findViewById(R.id.edt_pwd);
        btn_signin = findViewById(R.id.btn_signin);

        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        value = sp.getString("utype", "");
        username = sp.getString("username", "");
        uid = sp.getString("uid", "");

        if (value.equals("user")) {
            role = sp.getString("utype", "");
            startActivity(new Intent(SignInActivity.this, MainActivityUser.class));
            finish();
        }
        if (value.equals("gymadmin")) {
            role = sp.getString("utype", "");
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
            finish();
        }
        if (value.equals("delboy")) {
            role = sp.getString("utype", "");
            startActivity(new Intent(SignInActivity.this, DeliveryBoyActivity.class));
            finish();
        }

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = edt_uname.getText().toString();
                password = edt_pwd.getText().toString();
                if (email.isEmpty()) {
                    edt_uname.setError("Enter Username");
                    valid = false;
                } else if (password.isEmpty()) {
                    edt_pwd.setError("Enter Password");
                    valid = false;
                } else {
                    log(email, password);
                }
            }
        });
    }

    private void log(final String username, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Signing in...");
        progressDialog.show();
        ApInterface ap = RetrofitClass.getClient().create(ApInterface.class);
        Call<ResponseBody> ca = ap.login(username, password);
        ca.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    try {
                        String out = response.body().string();
                        String result = out.replaceAll("\r\n", "").trim();
                        String[] split = result.split(",");
                        String res = split[0];
                        if (res.equals("failed")) {
                            edt_pwd.setError("Incorrect Password");
                        }
                        if (res.equals("error")) {
                            edt_uname.setError("Incorrect Username");
                        }
                        if (res.equals("success")) {
                            role = split[4].trim();
                            id = split[1];
                            user_name = split[2];

                            SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor Ed = sp.edit();
                            Ed.putString("utype", role);
                            Ed.putString("uid", id);
                            Ed.putString("username", username);
                            Ed.commit();


                            if (role.equals("user")) {
                                startActivity(new Intent(SignInActivity.this, MainActivityUser.class));
                                finish();
                            }
                            else if (role.equals("gymadmin")) {
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                finish();
                            } else if (role.equals("delboy")) {
                                startActivity(new Intent(SignInActivity.this, DeliveryBoyActivity.class));
                                finish();
                            }

                        } else {
                            Toast.makeText(SignInActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Not Valid", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent  = new Intent(SignInActivity.this,WelcomePageActivity.class);
        startActivity(intent);
        finish();
    }
}