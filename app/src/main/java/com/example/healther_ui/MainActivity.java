package com.example.healther_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.healther_ui.databinding.ActivityMainBinding;
import com.example.healther_ui.databinding.FragmentFemaleBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainActivity;

    //BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    DiaryFragment diaryFragment = new DiaryFragment();
    LoginFragment loginFragment = new LoginFragment();
    HomeTrainingFragment homeTrainingFragment = new HomeTrainingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainActivity.getRoot());

        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, loginFragment).commit();

        mainActivity.navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, homeFragment).commit();
                        return true;
//                    case R.id.login:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, loginFragment).commit();
//                        return true;
                    case R.id.diary:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, diaryFragment).commit();
                        return true;
                    case R.id.home_training:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_layout, homeTrainingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}