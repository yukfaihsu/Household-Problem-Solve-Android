
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final String TAG = this.getClass().getCanonicalName();
    private final FirebaseFirestore DB;
    private final String COLLECTION_Users = "users";
    private final String FIELD_userEmail = "userEmail";
    private final String FIELD_password = "password";
    private final String FIELD_nickname = "nickname";
    private final String FIELD_phoneNumber = "phoneNumber";

    public MutableLiveData<User> currentUser = new MutableLiveData<>();

    public UserRepository() {
        DB = FirebaseFirestore.getInstance();
    }

    public void addUser(User newUser){
        try{
            Map<String, Object> data = new HashMap<>();
            data.put(FIELD_userEmail, newUser.getUserEmail());
            data.put(FIELD_password, newUser.getPassword());
            data.put(FIELD_nickname, newUser.getNickname());
            data.put(FIELD_phoneNumber, newUser.getPhoneNumber());

            DB.collection(COLLECTION_Users)
                    .document(newUser.getUserEmail())
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: Document added successfully, New User: " + newUser.toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Error creating document" + e.getLocalizedMessage());
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "addUser: " + ex.getLocalizedMessage());
        }
    }

    public void getUser(String userEmail){
        try{
            DB.collection(COLLECTION_Users)
                    .document(userEmail)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            User user;
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                    user = document.toObject(User.class);
                                    currentUser.postValue(user);
                                }
                                else {
                                    currentUser.postValue(null);
                                }
                            }
                            else {
                                Log.e(TAG, "get failed with ", task.getException());
                            }
                        }
                    });
        }catch(Exception ex){
            Log.e(TAG, "getUser: " + ex.getLocalizedMessage());
        }
    }

}
