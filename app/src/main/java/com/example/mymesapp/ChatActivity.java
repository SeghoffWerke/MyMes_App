package com.example.mymesapp;

import static com.example.mymesapp.UsersActivity.newIntent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    // Для передачи пользователей в переписке через Intent
    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";

    private TextView textViewTitle;
    private View onLineStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSent;

    private MessagesAdapter messagesAdapter;

    // Их получим из Интента
    private String currentUserId;
    private String otherUserId;

    private ChatViewModel viewModel;
    private ChatViewModelFactory viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        initViews();
        //Фэктори сам вызывает Вью Модель и сам передаёт в неё параметры
        viewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        messagesAdapter = new MessagesAdapter(currentUserId);
        recyclerViewMessages.setAdapter(messagesAdapter);
        // Если так запускать viewModel то приложение УПАДЁТ так как туда надо передать
        // 2 параметра
        // А сделать это можно через viewModel Фэктори
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);
        observeViewModel();

        // Добавляем слушатель на кнопку Отправить сообщение

        imageViewSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        editTextMessage.getText().toString().trim(),
                        currentUserId,
                        otherUserId
                );
                viewModel.sendMessage(message);
            }
        });

//        List<Message> messages = new ArrayList<>();
//
//        for (int i=0; i<10; i++){
//            Message message = new Message(
//                    "Текст " + i,
//                    currentUserId,
//                    otherUserId
//            );
//            messages.add(message);
//        }
//
//        for (int i=0; i<10; i++){
//            Message message = new Message(
//                    "Текст " + i,
//                    otherUserId,
//                    currentUserId
//            );
//            messages.add(message);
//        }
//        messagesAdapter.setMessages(messages);
    }

    // Подписываемся на все Лайф даты из Втю Модели
    public void observeViewModel(){
        viewModel.getMessagesList().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });

        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage != null){
                    Toast.makeText(ChatActivity.this,
                            errorMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSent) {
                // Очищаем поле для ввода текста
                editTextMessage.setText("");
            }
        });

        viewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //в верхней части экрана записываем Имя и Фамилию пользователя с кем переписываемся
                String userInfo = String.format("%s, %s", user.getName(), user.getLastName());
                textViewTitle.setText(userInfo);
            }
        });
    }
    private void initViews(){
        textViewTitle = findViewById(R.id.textViewTitle);
        onLineStatus = findViewById(R.id.onLineStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSent = findViewById(R.id.imageViewSent);
    }

    public static Intent newIntent (Context context, String currentUserId, String otherUserId){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }

}