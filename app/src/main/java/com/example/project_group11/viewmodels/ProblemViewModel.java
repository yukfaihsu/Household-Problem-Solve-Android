
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.Problem;
import com.example.project_group11.repositories.ProblemRepository;

import java.util.ArrayList;
import java.util.List;

public class ProblemViewModel extends AndroidViewModel {

    private final ProblemRepository repository = new ProblemRepository();
    private static ProblemViewModel instance;
    public MutableLiveData<ArrayList<Problem>> allProblems = this.repository.allProblems;

    public ProblemViewModel(@NonNull Application application) {
        super(application);
    }

    public static ProblemViewModel getInstance(Application application) {
        if(instance == null){
            instance = new ProblemViewModel(application);
        }
        return instance;
    }

    public ProblemRepository getRepository(){
        return this.repository;
    }

    public void addProblem(Problem newProblem){
        this.repository.addProblem(newProblem);
    }

    public void getAllProblems(String userEmail){
        this.repository.getAllProblems(userEmail);
    }

}
