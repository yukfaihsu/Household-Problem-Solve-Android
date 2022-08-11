
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.project_group11.models.Problem;
import com.example.project_group11.repositories.DetailRepository;

public class WriteReviewViewModel extends ViewModel {
    private final DetailRepository repository = new DetailRepository();

    public void writeReview(Problem problem, String review) {
        this.repository.writeReview(problem, review);
    }

}
