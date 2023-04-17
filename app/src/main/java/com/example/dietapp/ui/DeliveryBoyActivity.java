package com.example.dietapp.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.dietapp.R;
import com.example.dietapp.ui.Adapter.DeliveryBoyAdapter;
import com.example.dietapp.ui.Fragment.ActiveOrdersFragment;
import com.example.dietapp.ui.Fragment.DeliveredOrdersFragment;
import com.google.android.material.tabs.TabLayout;

public class DeliveryBoyActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    int tabPosition=0;
    Context mContext;
    ActiveOrdersFragment activeOrdersFragment;
    DeliveredOrdersFragment deliveredOrdersFragment;
    DeliveryBoyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy);

        tabLayout = findViewById(R.id.ref_tablout);
        viewPager = findViewById(R.id.ref_pager);
        mContext = this;
        activeOrdersFragment = new ActiveOrdersFragment();
        deliveredOrdersFragment = new DeliveredOrdersFragment();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_baseline_power_settings_new_24);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DeliveryBoyActivity.this);
                alert.setTitle("LogOut");
                alert.setMessage("Are you sure you want to Logout?");
                alert.setCancelable(false);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(DeliveryBoyActivity.this, SignInActivity.class));
                        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        finish();
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

        adapter = new DeliveryBoyAdapter(getSupportFragmentManager());
        adapter.addFragment2(activeOrdersFragment);
        adapter.addFragment2(deliveredOrdersFragment);

        tabLayout.addTab(tabLayout.newTab().setText("ACTIVE LIST"));
        tabLayout.addTab(tabLayout.newTab().setText("DELIVERED LIST"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                // viewPager.setCurrentItem(position);
                tabPosition = position;
                tabLayout.getTabAt(position).select();
                //viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {
                //viewPager.setCurrentItem(position);
                // actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        tabLayout.setOnTabSelectedListener(onTabSelectedListener(viewPager));
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

}