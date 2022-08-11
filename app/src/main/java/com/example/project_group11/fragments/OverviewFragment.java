
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.project_group11.adapters.ProblemAdapter;
import com.example.project_group11.databinding.FragmentOverviewBinding;
import com.example.project_group11.interfaces.OnProblemListRowClicked;
import com.example.project_group11.models.Problem;
import com.example.project_group11.viewmodels.OverviewViewModel;
import com.google.android.material.snackbar.Snackbar;

public class OverviewFragment extends Fragment implements OnProblemListRowClicked {
    private FragmentOverviewBinding binding;
    private OverviewViewModel vm;
    private ProblemAdapter problemAdapter;
    private String loggedInUser;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOverviewBinding.inflate(inflater, container, false);
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

        SharedPreferences sharedPref = getContext().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        loggedInUser = sharedPref.getString("USER_EMAIL","N/A");

        vm = new ViewModelProvider(requireActivity()).get(OverviewViewModel.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getContext(), android.R.layout.simple_list_item_1, vm.getSpinnerData());
        binding.spKind.setAdapter(adapter);

        this.problemAdapter = new ProblemAdapter(view.getContext(), vm.getDataToBeListed().getValue(), this::onRowClicked);
        this.binding.rvProblemList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        this.binding.rvProblemList.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        this.binding.rvProblemList.setAdapter(this.problemAdapter);

        vm.getDataToBeListed().observe(getViewLifecycleOwner(), newData -> {
            Log.d("life", "your are notified " + newData.toString());
            this.problemAdapter.updateAdapter(newData);
            Log.d("ABC", newData.toString());
        });

        binding.spKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    vm.getUpcomingTasks(loggedInUser);
                } else if (i == 1) {
                    vm.getProcessingTasks(loggedInUser);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onRowClicked(Problem problem) {
        NavDirections action = OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(problem, "overviewList");
        Navigation.findNavController(getView()).navigate(action);
    }
}
