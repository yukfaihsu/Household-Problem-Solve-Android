// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.project_group11.R;
import com.example.project_group11.databinding.ActivityMainBinding;
import com.example.project_group11.models.User;
import com.example.project_group11.viewmodels.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private SharedPreferences sharedPref;
    private String userNickName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPref = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);

        this.userNickName = sharedPref.getString("USER_NICKNAME", "");
        Log.d(TAG, "MainActivity: onCreate: userNickname: " + userNickName);
        binding.tvMainUserName.setText("Current User: " + this.userNickName);


        // Setting up the Bottom Navigation Bar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = binding.bottomNavView;
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

    // Functions to Logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:{
                if(sharedPref.contains("LOGIN")){
                    sharedPref.edit().remove("LOGIN");
                }
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}