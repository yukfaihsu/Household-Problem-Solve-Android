
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project_group11.models.Problem;
import com.example.project_group11.repositories.OverviewRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class OverviewViewModel extends ViewModel {
    private final ArrayList<String> spinnerData;
    private final OverviewRepository repository = new OverviewRepository();
    private final MutableLiveData<ArrayList<Problem>> DataToBeListed = this.repository.getDataToBeListed();

    public OverviewViewModel() {
        spinnerData = new ArrayList<String>(Arrays.asList("Upcoming tasks", "Processing problems"));
    }

    public ArrayList<String> getSpinnerData() {
        return spinnerData;
    }

    public MutableLiveData<ArrayList<Problem>> getDataToBeListed() {
        return DataToBeListed;
    }

    public void getUpcomingTasks(String username) {
        this.repository.findProblemsByKind(username, "upcomingTask");
    }

    public void getProcessingTasks(String username) {
        this.repository.findProblemsByKind(username, "processingTask");
        Log.d("ABC"," process1");
    }
}
