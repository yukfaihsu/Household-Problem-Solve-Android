
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_group11.R;
import com.example.project_group11.databinding.FragmentDetailBinding;
import com.example.project_group11.databinding.FragmentWriteReviewBinding;
import com.example.project_group11.models.Problem;
import com.example.project_group11.viewmodels.DetailViewModel;
import com.example.project_group11.viewmodels.WriteReviewViewModel;

public class WriteReviewFragment extends Fragment {
    private WriteReviewViewModel vm;
    private FragmentWriteReviewBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWriteReviewBinding.inflate(inflater, container, false);
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

        vm = new ViewModelProvider(requireActivity()).get(WriteReviewViewModel.class);

        Problem receivedProduct = WriteReviewFragmentArgs.fromBundle(getArguments()).getProblem();
        Log.d("print", receivedProduct.toString());

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.writeReview(receivedProduct, binding.etWriteReview.getText().toString());
                getActivity().onBackPressed();
                getActivity().onBackPressed();
            }
        });


    }
}