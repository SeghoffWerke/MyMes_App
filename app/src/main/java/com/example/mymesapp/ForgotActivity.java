package com.example.mymesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ForgotActivity extends AppCompatActivity {

    private EditText editTextEmailForgot;

    private Button buttonForgot;

    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initviews();

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        observeViewModel();

        buttonForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmailForgot.getText().toString().trim();
                if (!email.isEmpty()) {
                    viewModel.resetPassword(email);
                }
            }
        });
    }

    private void initviews(){
        editTextEmailForgot = findViewById(R.id.editTextEmailForgot);
        buttonForgot = findViewById(R.id.buttonForgot);
    }

    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null){
                    Toast.makeText(ForgotActivity.this,
                            errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.isSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess != null) {
                    Toast.makeText(ForgotActivity.this,
                            "On Your email adress sent message for reset password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public static Intent newIntent(Context context){
        return new Intent(context, ForgotActivity.class);
    }
}