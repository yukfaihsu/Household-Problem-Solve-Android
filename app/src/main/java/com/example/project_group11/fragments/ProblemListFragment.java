
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project_group11.R;
import com.example.project_group11.adapters.ProblemAdapter;
import com.example.project_group11.databinding.FragmentProblemListBinding;
import com.example.project_group11.interfaces.OnProblemListRowClicked;
import com.example.project_group11.models.Problem;
import com.example.project_group11.viewmodels.ProblemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProblemListFragment extends Fragment implements OnProblemListRowClicked, View.OnClickListener{

    private FragmentProblemListBinding binding;
    private ArrayList<Problem> problemsList;
    private ProblemAdapter problemAdapter;
    private ProblemViewModel problemViewModel;
    private final String TAG = this.getClass().getCanonicalName();
    private String userEmail;
    private SharedPreferences sharedPrefs;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentProblemListBinding.inflate(inflater, container, false);
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

        this.problemsList = new ArrayList<>();
        this.problemAdapter = new ProblemAdapter(view.getContext(), this.problemsList, this::onRowClicked);

        sharedPrefs = this.getActivity().getSharedPreferences("com.example.project_group11", Context.MODE_PRIVATE);
        this.userEmail = sharedPrefs.getString("USER_EMAIL", "");


        // Populate the recyclerView from the data from Firestore
        binding.rvProblemList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        binding.rvProblemList.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        binding.rvProblemList.setAdapter(this.problemAdapter);


        this.problemViewModel = ProblemViewModel.getInstance(getActivity().getApplication());
        this.problemViewModel.getAllProblems(userEmail);
        refreshRecyclerView();

        // Refresh the recyclerView
        binding.btnProblemListRefresh.setOnClickListener(this);
    }

    @Override
    public void onRowClicked(Problem problem) {
        NavDirections action = ProblemListFragmentDirections.actionProblemListFragmentToDetailFragment(problem, "problemList");
        Navigation.findNavController(getView()).navigate(action);
    }

    // Set the OnClickListener
    @Override
    public void onClick(View view) {
        if(view != null){
            switch(view.getId()){
                case R.id.btnProblemListRefresh:{
                    refreshRecyclerView();
                    break;
                }
            }
        }
    }

    public void refreshRecyclerView(){
        problemViewModel.getAllProblems(userEmail);
        problemViewModel.allProblems.observe(getViewLifecycleOwner(), new Observer<List<Problem>>() {
            @Override
            public void onChanged(List<Problem> problems) {
                problemAdapter.updateAdapter((ArrayList<Problem>) problems);
            }
        });
    }
}