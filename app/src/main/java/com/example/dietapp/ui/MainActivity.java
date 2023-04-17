package com.example.dietapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dietapp.R;
import com.example.dietapp.ui.Fragment.HomeFragment;
import com.example.dietapp.ui.Fragment.MyOrderFragment;
import com.example.dietapp.ui.Fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    MyOrderFragment myOrderFragment;
    LinearLayout profile,home,orders;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("DietApp");
        frameLayout = findViewById(R.id.frmlout);
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        myOrderFragment  = new MyOrderFragment();
        profile= findViewById(R.id.lin_profile);
        home = findViewById(R.id.lin_home);
        orders = findViewById(R.id.lin_order);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frmlout, homeFragment);
        transaction.commit();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout, profileFragment);
                transaction.commit();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout, myOrderFragment);
                transaction.commit();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout, homeFragment);
                transaction.commit();
            }
        });
        

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }
}