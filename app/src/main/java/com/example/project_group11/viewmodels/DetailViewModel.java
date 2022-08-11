
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.project_group11.models.Problem;
import com.example.project_group11.repositories.DetailRepository;

public class DetailViewModel extends ViewModel {
    private final DetailRepository repository = new DetailRepository();

    public void confirm(String documentID, String userEmail, String userName) {
        this.repository.confirm(documentID, userEmail, userName);
    }

    public void cancel(String documentID) {
        this.repository.delete(documentID);
    }

    public void complete(Problem completedProblem) {
        this.repository.complete(completedProblem);
    }

}
