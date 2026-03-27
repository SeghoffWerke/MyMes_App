package com.example.mymesapp;


import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<User> users = new ArrayList<>();

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();

    }
    // Добавляем ссылку на Интерфейс слушателя Клика
    private OnUserClickListener onUserClickListener;
    // Добавляем сеттер на ссылку интерфейса слушателя Клика
    public void setOnUserClickListener(OnUserClickListener onUserClickListener) {
        this.onUserClickListener = onUserClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,
                parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user = users.get(position);
        // Заполняем одну строчку в Адаптере
        String userInfo = String.format("%s, %s, %s", user.getName(), user.getLastName(), user.getAge());
        holder.textViewUserInfo.setText(userInfo);
        // Выбираем цвет он-лайн или нет
        int bgResId;
        if (user.getOnline()){
            bgResId = R.drawable.circle_green;
        } else {
            bgResId = R.drawable.circle_red;
        }
        // Устанавливаем признак он-лайна
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), bgResId);
        holder.onLineStatus.setBackground(background);
        // Устанавливаем слушатель Клика по User
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUserClickListener != null){
                    onUserClickListener.onUserClick(user);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    // Добавляем интерфейс для слушателя Клика по элементу Адаптера
    interface OnUserClickListener{
        void onUserClick(User user);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewUserInfo;

        private View onLineStatus;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserInfo = itemView.findViewById(R.id.textViewUserInfo);
            onLineStatus = itemView.findViewById(R.id.viewOnlineStatus);
        }
    }

}
