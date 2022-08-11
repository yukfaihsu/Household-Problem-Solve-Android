
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.Problem;
import com.example.project_group11.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemRepository {

    private final String TAG = this.getClass().getCanonicalName();
    private final FirebaseFirestore DB;
    private final String COLLECTION_Problems = "problems";
    private final String FIELD_userEmail = "userEmail";
    private final String FIELD_userNickname = "userNickname";
    private final String FIELD_userMobile = "userMobile";
    private final String FIELD_title = "title";
    private final String FIELD_details = "details";
    private final String FIELD_remuneration = "remuneration";
    private final String FIELD_date = "date";
    private final String FIELD_address = "address";
    private final String FIELD_coordinates = "coordinates";
    private final String FIELD_solverEmail = "solverEmail";
    private final String FIELD_solverName = "solverName";
    private final String FIELD_reviewFromCustomer = "reviewFromCustomer";

    public MutableLiveData<ArrayList<Problem>> allProblems = new MutableLiveData<>();

    public ProblemRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public void addProblem(Problem newProblem){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_userEmail, newProblem.getUserEmail());
            data.put(FIELD_userNickname, newProblem.getUserNickname());
            data.put(FIELD_userMobile, newProblem.getUserMobile());
            data.put(FIELD_title, newProblem.getTitle());
            data.put(FIELD_details, newProblem.getDetails());
            data.put(FIELD_remuneration, newProblem.getRemuneration());
            data.put(FIELD_date, newProblem.getDate());
            data.put(FIELD_address, newProblem.getAddress());
            data.put(FIELD_coordinates, newProblem.getCoordinates());
            data.put(FIELD_solverEmail, newProblem.getSolverEmail());
            data.put(FIELD_solverName, newProblem.getSolverName());
            data.put(FIELD_reviewFromCustomer, newProblem.getReviewFromCustomer());

            DB.collection(COLLECTION_Problems)
                    .document()
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document added successfully, New User: " + newProblem.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error creating document" + e.getLocalizedMessage());
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "Catch: addProblem: " + ex.getLocalizedMessage());
        }

    }

    public void getAllProblems(String userEmail) {
        try {
            DB.collection(COLLECTION_Problems)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ArrayList<Problem> problemList = new ArrayList<>();

                            if (queryDocumentSnapshots.isEmpty()) {
                                Log.e(TAG, "onSuccess: No data retrieved");
                            }
                            else {
                                Log.d(TAG, "onSuccess: queryDocumentSnapshots" + queryDocumentSnapshots.getDocumentChanges());

                                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges()) {
                                    Problem currentProblem = documentChange.getDocument().toObject(Problem.class);
                                    if (!currentProblem.getUserEmail().equals(userEmail)&& !currentProblem.getSolverEmail().equals(userEmail)) {
                                        problemList.add(currentProblem);
                                    }
                                    Log.d(TAG, "onSuccess: currentProblem " + currentProblem.toString());
                                }
                            }
                            allProblems.postValue(problemList);
                        }
                    });
        } catch (Exception ex) {
            Log.e(TAG, "getAllFriends: " + ex.getLocalizedMessage());
        }
    }
}
