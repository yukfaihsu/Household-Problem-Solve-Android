
// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.project_group11.models.User;
import com.example.project_group11.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository repository = new UserRepository();
    private static UserViewModel instance;
    public MutableLiveData<User> currentUser = repository.currentUser;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public static UserViewModel getInstance(Application application) {
        if(instance == null){
            instance = new UserViewModel(application);
        }
        return instance;
    }

    public UserRepository getRepository(){
        return this.repository;
    }

    public void addUser(User newUser){
        this.repository.addUser(newUser);
    }

    public void getUser(String userEmail){
        this.repository.getUser(userEmail);
    }
}
