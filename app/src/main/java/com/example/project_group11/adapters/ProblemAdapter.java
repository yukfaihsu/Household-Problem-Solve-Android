package com.example.project_group11.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_group11.databinding.CustomProblemListLayoutBinding;
import com.example.project_group11.interfaces.OnProblemListRowClicked;
import com.example.project_group11.models.Problem;

import java.util.ArrayList;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder> {

    private final Context context;
    private ArrayList<Problem> problemsArray;
    private final OnProblemListRowClicked clickListener;

    public ProblemAdapter(Context context, ArrayList<Problem> problems, OnProblemListRowClicked clickListener){
        this.problemsArray = problems;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProblemViewHolder(CustomProblemListLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        Problem currProblem = problemsArray.get(position);
        holder.bind(context, currProblem, clickListener);
    }

    @Override
    public int getItemCount() {
        return problemsArray.size();
    }

    public static class ProblemViewHolder extends RecyclerView.ViewHolder{
        CustomProblemListLayoutBinding itemBinding;

        public ProblemViewHolder(CustomProblemListLayoutBinding binding){
            super(binding.getRoot());
            this.itemBinding = binding;
        }

        public void bind(Context context, Problem currProblem, OnProblemListRowClicked clickListener){
            itemBinding.tvProblemListTitle.setText(currProblem.getTitle());
            itemBinding.tvProblemListDate.setText("Expect to finish before " + currProblem.getDate());

            itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onRowClicked(currProblem);
                }
            });
        }
    }

    public void updateAdapter(ArrayList<Problem> newData) {
        this.problemsArray = newData;
        notifyDataSetChanged();
    }
}

