// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.Problem;
import com.example.project_group11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DetailRepository {
    private final FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private final String COLLECTION_pastProblems = "pastProblems";
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

    public void confirm(String documentID, String userEmail, String userName) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("solverEmail", userEmail);
        data.put("solverName", userName);
        try{
            DB.collection("problems")
                    .document(documentID)
                    .update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.e("ABC", "onSuccess: document successfully updated");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("ABC", "onFailure: Unable to update document" + e.getLocalizedMessage());
                        }
                    });
        } catch (Exception ex) {

        }

    }

    public void delete(String documentID) {
        try {
            DB.collection("problems").document(documentID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("ABC", "DocumentSnapshot successfully deleted!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("ABC", "Error deleting document", e);
                        }
                    });
        } catch (Exception ex) {

        }
    }

    public void complete(Problem completedProblem) {
        Log.d("ABC", "compete called");

        delete(completedProblem.getDocumentID());

        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_userEmail, completedProblem.getUserEmail());
            data.put(FIELD_userNickname, completedProblem.getUserNickname());
            data.put(FIELD_userMobile, completedProblem.getUserMobile());
            data.put(FIELD_title, completedProblem.getTitle());
            data.put(FIELD_details, completedProblem.getDetails());
            data.put(FIELD_remuneration, completedProblem.getRemuneration());
            data.put(FIELD_date, completedProblem.getDate());
            data.put(FIELD_address, completedProblem.getAddress());
            data.put(FIELD_coordinates, completedProblem.getCoordinates());
            data.put(FIELD_solverEmail, completedProblem.getSolverEmail());
            data.put(FIELD_solverName, completedProblem.getSolverName());
            data.put(FIELD_reviewFromCustomer, completedProblem.getReviewFromCustomer());

            DB.collection(COLLECTION_pastProblems)
                    .document()
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("ABC", "onSuccess: Document added successfully, New User: " + completedProblem.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("ABC", "onFailure: Error creating document" + e.getLocalizedMessage());

                        }
                    });
        } catch(Exception ex) {
            Log.e("ABC", "Catch: addProblem: " + ex.getLocalizedMessage());
        }
    }

    public void writeReview(Problem problemTobeWritten, String review) {
        problemTobeWritten.setReviewFromCustomer(review);
        complete(problemTobeWritten);
        Log.e("ABC", "onSuccess: document successfully updated");

    }

}
