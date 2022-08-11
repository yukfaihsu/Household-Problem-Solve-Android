
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project_group11.models.Problem;
import com.example.project_group11.repositories.HistoryRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryViewModel extends ViewModel {
    private final ArrayList<String> spinnerData;
    private final HistoryRepository repository = new HistoryRepository();
    private final MutableLiveData<ArrayList<Problem>> DataToBeListed = this.repository.getDataToBeListed();

    public HistoryViewModel() {
        spinnerData = new ArrayList<String>(Arrays.asList("Problems you posted", "Problems you solved"));
    }

    public ArrayList<String> getSpinnerData() {
        return spinnerData;
    }

    public MutableLiveData<ArrayList<Problem>> getDataToBeListed() {
        return DataToBeListed;
    }

    public void getPostedProblems(String username) {
        this.repository.findProblemsByKind(username, "postedProblem");
    }

    public void getSolvedProblems(String username) {
        this.repository.findProblemsByKind(username, "solvedProblem");
    }

}
