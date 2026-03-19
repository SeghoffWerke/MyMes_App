package com.example.mymesapp;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UsersViewModel extends ViewModel {

    private FirebaseAuth auth;

    private MutableLiveData<FirebaseUser> userSys = new MutableLiveData<>();
    public UsersViewModel() {
        auth = FirebaseAuth.getInstance();
        // Если пользователь уже залогинился, то переход сразу на окно с пользователями
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
             //   if (firebaseAuth.getCurrentUser() == null){
                    userSys.setValue(firebaseAuth.getCurrentUser());
             //   }
            }
        });
    }
    public MutableLiveData<FirebaseUser> getUserSys() {
        return userSys;
    }

    public void logOff(){
        auth.signOut();
    }
}
