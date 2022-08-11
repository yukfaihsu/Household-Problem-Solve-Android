// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.project_group11.R;
import com.example.project_group11.databinding.ActivitySignUpBinding;
import com.example.project_group11.models.User;
import com.example.project_group11.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivitySignUpBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private FirebaseAuth mAuth;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.mAuth = FirebaseAuth.getInstance();
        this.userViewModel = UserViewModel.getInstance(this.getApplication());
        this.binding.btnCreateAccount.setOnClickListener(this);

    }

    // Set the OnClickListener of the button
    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.btnCreateAccount:{
                    Log.d(TAG, "onClick: btnCreateAccount Clicked");
                    validateData();
                    break;
                }
            }
        }
    }

    // Function to validate the inputs entered
    public void validateData(){
        boolean validData = true;
        String email = "";
        String password = "";
        String nickname = "";
        String phoneNumber = "";

        if(binding.etSignUpEmail.getText().toString().isEmpty()){
            validData = false;
            binding.etSignUpEmail.setError("Email cannot be empty");
        }
        else{
            email = binding.etSignUpEmail.getText().toString();
        }

        if (this.binding.etSignUpPassword.getText().toString().isEmpty()){
            this.binding.etSignUpPassword.setError("Password Cannot be Empty");
            validData = false;
        }
        else {
            if (this.binding.etConfirmPassword.getText().toString().isEmpty()) {
                this.binding.etConfirmPassword.setError("Confirm Password Cannot be Empty");
                validData = false;
            }
            else {

                if (!this.binding.etSignUpPassword.getText().toString().equals(this.binding.etConfirmPassword.getText().toString())) {
                    this.binding.etConfirmPassword.setError("Both passwords must be same");
                    validData = false;
                }
                else{
                    password = this.binding.etSignUpPassword.getText().toString();
                }
            }
        }

        if(binding.etNickName.getText().toString().isEmpty()){
            validData = false;
            binding.etNickName.setError("Nickname cannot be empty");
        }
        else{
            nickname = binding.etNickName.getText().toString();
        }

        if(binding.etPhone.getText().toString().isEmpty()){
            validData = false;
            binding.etPhone.setError("Phone number cannot be empty");
        }
        else{
            if(TextUtils.isDigitsOnly(binding.etPhone.getText())){
                if(binding.etPhone.getText().toString().length() == 10){
                    phoneNumber = binding.etPhone.getText().toString();
                }
                else{
                    validData = false;
                    binding.etPhone.setError("Invalid phone number");
                }
            }
            else{
                validData = false;
                binding.etPhone.setError("Invalid phone number");
            }
        }

        if(validData){
            createAccount(email, password);
            User newUser = new User(email, password, nickname, phoneNumber);

            // Add new user in Database
            userViewModel.addUser(newUser);
            Log.d(TAG, "validateData: New User: " + newUser.toString());

            binding.etSignUpEmail.setText("");
            binding.etSignUpPassword.setText("");
            binding.etConfirmPassword.setText("");
            binding.etNickName.setText("");
            binding.etPhone.setText("");
        }
        else{
            Snackbar.make(binding.getRoot(), "Please enter all form fields correctly", Snackbar.LENGTH_SHORT).show();
        }

    }

    // Function to create the account
    public void createAccount(String email, String password){
        this.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: Account created successfully.");
                    Snackbar.make(binding.getRoot(), "Account created", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    Log.e(TAG, "onComplete: Fail to create the user. " + task.getException() + task.getException().getLocalizedMessage());
                    Snackbar.make(binding.getRoot(), "Cannot create account", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


}