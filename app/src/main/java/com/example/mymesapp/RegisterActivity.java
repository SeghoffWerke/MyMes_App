package com.example.mymesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmailReg;
    private EditText editTextPassReg;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmailReg.getText().toString().trim();
                String password = editTextPassReg.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
            }
        });
    }

    public static Intent newIntent(Context context){
        return new Intent(context, RegisterActivity.class);
    }

    private void initViews(){
        editTextEmailReg = findViewById(R.id.editTextEmailReg);
        editTextPassReg = findViewById(R.id.editTextPassReg);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonReg = findViewById(R.id.buttonReg);
    }
}