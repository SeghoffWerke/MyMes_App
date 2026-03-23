package com.example.mymesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UsersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;
    private UsersViewModel viewModel;
    private Button buttonLogOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        buttonLogOff = findViewById(R.id.buttonLogOff);
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        observeViewModel();

        buttonLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.logOff();
            }
        });

//        List<User> users = new ArrayList<>();
//        for (int i=0; i<20; i++){
//            User user = new User(
//              "id " + i,
//              "name " + i,
//              "last name " + i,
//              i, new Random().nextBoolean()
//            );
//            users.add(user);
//        }
//        usersAdapter.setUsers(users);
    }

    private void initViews(){
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, UsersActivity.class);
    }

    private void observeViewModel (){
        viewModel.getUserSys().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null){
                    Intent intent = MainActivity.newIntent(UsersActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Toast.makeText(UsersActivity.this, "observer yes", Toast.LENGTH_SHORT).show();
                usersAdapter.setUsers(users);
            }
        });
    }
}