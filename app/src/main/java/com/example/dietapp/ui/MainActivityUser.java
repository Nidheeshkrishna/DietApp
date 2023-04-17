package com.example.dietapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.dietapp.R;
import com.example.dietapp.ui.Fragment.UserHomeFragment;
import com.example.dietapp.ui.Fragment.UserProfileFragment;
import com.example.dietapp.ui.Fragment.UserVideoFragment;
import com.example.dietapp.ui.Fragment.UsrTrnrsListFragment;

public class MainActivityUser extends AppCompatActivity {

    LinearLayout usr_home,usr_profile,videos,trnrlist;
    UserHomeFragment homeFragment;
    UserProfileFragment userProfileFragment;
    UserVideoFragment videoFragment;
    UsrTrnrsListFragment trnrsListFragment;
    UsrGuideTrnrListFragment guideListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        usr_home = findViewById(R.id.lin_usrhome);
        usr_profile = findViewById(R.id.lin_usrprofile);
        videos = findViewById(R.id.lin_vid);
        trnrlist = findViewById(R.id.lin_trnrlist);
        homeFragment = new UserHomeFragment();
        userProfileFragment = new UserProfileFragment();
        videoFragment = new UserVideoFragment();
        trnrsListFragment = new UsrTrnrsListFragment();
        guideListFragment=new UsrGuideTrnrListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frmlout_usr, homeFragment);
        transaction.commit();

        usr_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout_usr, userProfileFragment);
                transaction.commit();
            }
        });

        usr_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout_usr, homeFragment);
                transaction.commit();
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout_usr, videoFragment);
                transaction.commit();
            }
        });

        trnrlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frmlout_usr, trnrsListFragment);
                transaction.commit();
            }
        });
    }
}