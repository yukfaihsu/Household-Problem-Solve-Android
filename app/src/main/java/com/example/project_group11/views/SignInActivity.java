// Ka Chun Wan 101389677
// Yuk Fai Hsu 101395298
//Group 11

package com.example.project_group11.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.project_group11.R;
import com.example.project_group11.databinding.ActivitySignInBinding;
import com.example.project_group11.models.User;
import com.example.project_group11.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivitySignInBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPrefs;
    private UserViewModel userViewModel;
    private String userNickName = "";
    private String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.mAuth = FirebaseAuth.getInstance();

        this.userViewModel = UserViewModel.getInstance(getApplication());

        sharedPrefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);


        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);

        if(sharedPrefs.getBoolean("LOGIN", false)){
            this.goToMainActivity();
        }

        userViewModel.currentUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userNickName = user.getNickname();
                userPhone = user.getPhoneNumber();
                sharedPrefs.edit().putString("USER_NICKNAME", userNickName).apply();
                sharedPrefs.edit().putString("USER_PHONE", userPhone).apply();
                goToMainActivity();
            }
        });
    }

    // Reset the login screen after logout
    @Override
    protected void onResume() {
        super.onResume();
        binding.etEmail.setText("");
        binding.etPassword.setText("");
        binding.swRememberMe.setChecked(false);
    }

    // Set the OnClickListener of the buttons
    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.btnSignUp:{
                    Log.d(TAG, "onClick: btnSignUp Pressed");
                    this.goToSignUp();
                    break;
                }

                case R.id.btnSignIn:{
                    Log.d(TAG, "onClick: btnSignIn Pressed");
                    this.validateData();
                    break;
                }
            }
        }
    }

    // Function to go to Sign Up Screen
    private void goToSignUp(){
        Intent intentSignUp = new Intent(this, SignUpActivity.class);
        startActivity(intentSignUp);
    }

    // Function to check whether the credentials are matched or not
    public void validateData(){
        boolean validData = true;
        String email = "";
        String password = "";

        if (this.binding.etEmail.getText().toString().isEmpty()){
            this.binding.etEmail.setError("Email Cannot be Empty");
            validData = false;
        }
        else{
            email = this.binding.etEmail.getText().toString();
        }

        if (this.binding.etPassword.getText().toString().isEmpty()){
            this.binding.etPassword.setError("Password Cannot be Empty");
            validData = false;
        }
        else {
            password = this.binding.etPassword.getText().toString();
        }

        if (validData){
            this.signIn(email, password);
        }
        else{
            Snackbar.make(binding.getRoot(), "Please enter correct credentials", Snackbar.LENGTH_SHORT).show();
        }
    }

    // Function to Sign in to Main Activity Screen
    private void signIn(String email, String password){
        this.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "onComplete: Sign In Successful");
                    saveUserInfo(email);
                    rememberLogin();
                }
                else{
                    Log.e(TAG, "onComplete: Sign In Failed" + task.getException().getLocalizedMessage() );
                    Snackbar.make(binding.getRoot(), task.getException().getLocalizedMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveUserInfo(String userEmail){
        sharedPrefs.edit().putString("USER_EMAIL", userEmail).apply();
        userViewModel.getUser(userEmail);
        userViewModel.currentUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userNickName = user.getNickname();
                userPhone = user.getPhoneNumber();
                sharedPrefs.edit().putString("USER_NICKNAME", userNickName).apply();
                sharedPrefs.edit().putString("USER_PHONE", userPhone).apply();
            }
        });

    }

    // Set the remember Login if the user checks the switch
    public void rememberLogin(){
        if(binding.swRememberMe.isChecked()){
            sharedPrefs.edit().putBoolean("LOGIN", true).apply();
        }
        else{
            if(sharedPrefs.contains("LOGIN"))
                sharedPrefs.edit().remove("LOGIN").apply();
        }
    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}