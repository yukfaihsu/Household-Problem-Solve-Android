
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project_group11.R;
import com.example.project_group11.databinding.FragmentDetailBinding;
import com.example.project_group11.databinding.FragmentHistoryBinding;
import com.example.project_group11.models.LocationHelper;
import com.example.project_group11.models.Problem;
import com.example.project_group11.models.User;
import com.example.project_group11.viewmodels.DetailViewModel;
import com.example.project_group11.viewmodels.HistoryViewModel;
import com.example.project_group11.viewmodels.UserViewModel;
import com.example.project_group11.views.MainActivity;
import com.example.project_group11.views.MapsActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class DetailFragment extends Fragment {
    private FragmentDetailBinding binding;
    private DetailViewModel vm;
    HashMap<String, Double> coordinates;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
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

        vm = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);

        Problem receivedProduct = DetailFragmentArgs.fromBundle(getArguments()).getProblem();
        Log.d("print", receivedProduct.toString());

        String comeFrom = DetailFragmentArgs.fromBundle(getArguments()).getComeFrom();

        this.coordinates = receivedProduct.getCoordinates();

        SharedPreferences sharedPref = getContext().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        String loggedInUserEmail = sharedPref.getString("USER_EMAIL","N/A");
        String loggedInUserName = sharedPref.getString("USER_NICKNAME","N/A");

        int confirmVisibility = View.GONE;
        int cancelVisibility = View.GONE;
        int completeVisibility = View.GONE;
        int reviewVisibility = View.GONE;
        int reviewPromptVisibility = View.GONE;

        // show different layout when coming from different fragment
        switch (comeFrom) {
            case "overviewList":
                if (receivedProduct.getUserEmail().equals(loggedInUserEmail)) {
                    if (receivedProduct.getSolverEmail().isEmpty()) {
                        cancelVisibility = View.VISIBLE;
                    } else {
                        completeVisibility = View.VISIBLE;
                    }
                }
                break;
            case "historyList":
                reviewVisibility = View.VISIBLE;
                reviewPromptVisibility = View.VISIBLE;
                break;
            case "problemList":
                confirmVisibility = View.VISIBLE;
                break;
        }

        // set visibility for detail screen
        binding.btnConfirm.setVisibility(confirmVisibility);
        binding.btnCancel.setVisibility(cancelVisibility);
        binding.btnComplete.setVisibility(completeVisibility);
        binding.tvReview.setVisibility(reviewVisibility);
        binding.tvReviewPrompt.setVisibility(reviewPromptVisibility);

        binding.tvTitle.setText(receivedProduct.getTitle());
        binding.tvDescription.setText(receivedProduct.getDetails());
        binding.tvName.setText(receivedProduct.getUserNickname());
        binding.tvPhone.setText(receivedProduct.getUserMobile());
        binding.tvRenumeration.setText(String.valueOf(receivedProduct.getRemuneration()));
        binding.tvSolver.setText(receivedProduct.getSolverName());
        binding.tvAddress.setText(receivedProduct.getAddress());
        binding.tvDate.setText(receivedProduct.getDate());
        binding.tvReview.setText(receivedProduct.getReviewFromCustomer());

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.confirm(receivedProduct.getDocumentID(), loggedInUserEmail, loggedInUserName);
                getActivity().onBackPressed();
                Log.d("ABC", "btnConfirm called");
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.cancel(receivedProduct.getDocumentID());
                getActivity().onBackPressed();
            }

        });

        binding.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("ABC", "compete called");
                AlertDialog.Builder alertDialog =
                        new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Would you like to write a review?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavDirections action = DetailFragmentDirections.actionDetailFragmentToWriteReviewFragment(receivedProduct);
                        Navigation.findNavController(getView()).navigate(action);
                    }
                });
                alertDialog.setNeutralButton("Skip",(dialog, which) -> {
                    receivedProduct.setReviewFromCustomer("No review");
                    vm.complete(receivedProduct);
                    getActivity().onBackPressed();
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }

        });

        binding.btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coordinates.isEmpty()) {
                    Snackbar.make(binding.getRoot(), "Cannot show the address in the map.", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    goToMap();
                }
            }
        });

    }

    private void goToMap(){
        Intent intentToMap = new Intent(getActivity(), MapsActivity.class);
        intentToMap.putExtra("EXTRA_LAT", this.coordinates.get("latitude"));
        intentToMap.putExtra("EXTRA_LNG", this.coordinates.get("longitude"));
        startActivity(intentToMap);
    }
}