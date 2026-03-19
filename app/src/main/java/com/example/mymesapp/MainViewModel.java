package com.example.mymesapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel{

   private FirebaseAuth auth;

   private MutableLiveData<String> error = new MutableLiveData<>();
   private MutableLiveData<FirebaseUser> userSys = new MutableLiveData<>();


    public MainViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<FirebaseUser> getUserSys() {
        return userSys;
    }

    public void logIn (String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                userSys.setValue(authResult.getUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}
