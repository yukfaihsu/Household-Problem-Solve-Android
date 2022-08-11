
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.project_group11.R;
import com.example.project_group11.databinding.FragmentProblemPostingBinding;
import com.example.project_group11.models.LocationHelper;
import com.example.project_group11.models.Problem;
import com.example.project_group11.viewmodels.ProblemViewModel;
import com.example.project_group11.views.MapsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ProblemPostingFragment extends Fragment implements  View.OnClickListener{

    private FragmentProblemPostingBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private SharedPreferences sharedPrefs;
    private ProblemViewModel problemViewModel;
    private String currUserEmail = "";
    private String currUserNickname = "";
    private String currUserPhone = "";
    HashMap<String, Double> coordinates = new HashMap<>();
    private LocationHelper locationHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProblemPostingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefs = this.getActivity().getSharedPreferences("com.example.project_group11", Context.MODE_PRIVATE);
        this.currUserEmail = sharedPrefs.getString("USER_EMAIL", "");
        this.currUserNickname = sharedPrefs.getString("USER_NICKNAME", "");
        this.currUserPhone = sharedPrefs.getString("USER_PHONE", "");

        this.problemViewModel = ProblemViewModel.getInstance(getActivity().getApplication());

        this.locationHelper = LocationHelper.getInstance();

        binding.btnShowAddress.setOnClickListener(this);
        binding.btnPostProblem.setOnClickListener(this);


        // Setting up the calendarView
        binding.calendarView.setMinDate(new Date().getTime());
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                binding.tvSelectedDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        });
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        binding.tvSelectedDate.setText(currentDate);

    }

    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.btnShowAddress:{
                    Log.d(TAG, "onClick: btnShowAddress Pressed");
                    if(binding.etAddress.getText().toString().isEmpty()){
                        binding.etAddress.setError("Address cannot be empty");
                    }
                    else{
                        Log.d(TAG, "onClick: Perform reverse geocoding to get coordinates from address");
                        getCoordinatesFromAddress(binding.etAddress.getText().toString());
                        if(coordinates.isEmpty()){
                            Snackbar.make(binding.getRoot(), "Cannot show the address in the map, but you can keep the address for the solver to find you.", Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            goToMap();
                        }
                    }
                    break;
                }

                case R.id.btnPostProblem:{
                    Log.d(TAG, "onClick: btnPostProblem Pressed");
                    this.postProblem();
                    break;
                }
            }
        }
    }

    private void postProblem(){
        boolean validData = true;
        String title = "";
        String details = "";
        double remuneration = 0;
        String date = "";
        String address = "";

        if(binding.etTitle.getText().toString().isEmpty()){
            validData = false;
            binding.etTitle.setError("Title cannot be empty");
        }
        else{
            title = binding.etTitle.getText().toString();
        }

        if(binding.etDetails.getText().toString().isEmpty()){
            validData = false;
            binding.etDetails.setError("Details cannot be empty");
        }
        else{
            details = binding.etDetails.getText().toString();
        }

        if(binding.etRemuneration.getText().toString().isEmpty()){
            validData = false;
            binding.etDetails.setError("Remunerations cannot be empty");
        }
        else{
            if(TextUtils.isDigitsOnly(binding.etRemuneration.getText())) {
                double currRemuneration = Double.parseDouble(binding.etRemuneration.getText().toString());
                if (currRemuneration >= 0) {
                    remuneration = currRemuneration;
                }
                else {
                    validData = false;
                    binding.etRemuneration.setError("Remunerations must be a positive number");
                }
            }
            else {
                validData = false;
                binding.etRemuneration.setError("Remunerations must be a positive number");
            }
        }

        date = binding.tvSelectedDate.getText().toString();

        if(binding.etAddress.getText().toString().isEmpty()){
            validData = false;
            binding.etAddress.setError("Address cannot be empty");
        }
        else{
            address = binding.etAddress.getText().toString();
            getCoordinatesFromAddress(address);
        }

        if(validData){
            Problem newProblem = new Problem(currUserEmail, currUserNickname, currUserPhone, title,
                    details, remuneration, date, address, coordinates, "", "", "");

            // Add new user in Database
            problemViewModel.addProblem(newProblem);
            Log.d(TAG, "validateData: New Problem: " + newProblem.toString());
            Snackbar.make(binding.getRoot(), "Problem posted successfully", Snackbar.LENGTH_SHORT).show();

            binding.etTitle.setText("");
            binding.etDetails.setText("");
            binding.etRemuneration.setText("");
            binding.etAddress.setText("");
            binding.tvSelectedDate.setText("");
            binding.calendarView.setDate(new Date().getTime());
        }
        else{
            Snackbar.make(binding.getRoot(), "Please enter all form fields correctly", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void getCoordinatesFromAddress(String address){
        Log.d(TAG, "getCoordinatesFromAddress: Perform reverse geocoding to get coordinates from address");
        LatLng obtainedCoordinates = this.locationHelper.performGeocoding(getActivity(), address);
        coordinates.clear();
        if(obtainedCoordinates != null){
            coordinates.put("latitude", obtainedCoordinates.latitude);
            coordinates.put("longitude", obtainedCoordinates.longitude);
        }
    }

    private void goToMap(){
        Intent intentToMap = new Intent(getActivity(), MapsActivity.class);
        intentToMap.putExtra("EXTRA_LAT", this.coordinates.get("latitude"));
        intentToMap.putExtra("EXTRA_LNG", this.coordinates.get("longitude"));
        startActivity(intentToMap);
    }
}