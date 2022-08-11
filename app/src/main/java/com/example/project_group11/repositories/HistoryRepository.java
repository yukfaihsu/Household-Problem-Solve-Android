// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.Problem;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryRepository {
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();

    private final String COLLECTION_Problems = "pastProblems";
    private final String FIELD_solverEmail = "solverEmail";
    private final String FIELD_userEmail = "userEmail";

    private MutableLiveData<ArrayList<Problem>> dataToBeListed = new MutableLiveData<>(new ArrayList<>());

    public MutableLiveData<ArrayList<Problem>> getDataToBeListed() {
        return dataToBeListed;
    }

    public void findProblemsByKind(String userEmail, String kind) {

        String field = (kind == "solvedProblem")? FIELD_solverEmail : FIELD_userEmail;
        try {
            DB.collection(COLLECTION_Problems)
                    .whereEqualTo(field, userEmail)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            ArrayList<Problem> problemList = new ArrayList<>();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Problem p = document.toObject(Problem.class);
                                problemList.add(p);
                            }
                            dataToBeListed.postValue(problemList);
                            Log.d("ABC", "HERE YOU ARE HERE");
                        }
                    });
        } catch (Exception ex) {

        }
    }
}
