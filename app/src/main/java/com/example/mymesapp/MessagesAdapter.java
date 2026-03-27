package com.example.mymesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{
//Константы для определения чьё сообщение
    private static final int MY_MESSAGE_VIEW_TYPE = 100;
    private static final int OTHER_MESSAGE_VIEW_TYPE = 333;

    private List<Message> messages = new ArrayList<>();

    //  текущий пользователь
    private String currentId;

    public MessagesAdapter(String currentId) {
        this.currentId = currentId;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // сюда во viewType прилетает значение от переопределенного getItemViewType его и проверяем
        int layoutResId;
        if (viewType == MY_MESSAGE_VIEW_TYPE){
            layoutResId = R.layout.my_message;
        } else {
            layoutResId = R.layout.other_message;
        }
        // вставляем выбранный выше макет
        View view = LayoutInflater.from(parent.getContext()).inflate(
                layoutResId,parent,false
        );
        return new MessageViewHolder(view);
    }

    // Для определения какой макет должен быть наш или другой
    @Override
    public int getItemViewType(int position) {
        // переопределяем метод
        Message message = messages.get(position);
        if (message.getSenderId().equals(currentId)){
            return MY_MESSAGE_VIEW_TYPE;
        } else {
            return OTHER_MESSAGE_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageItem.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView messageItem;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageItem = itemView.findViewById(R.id.messageItem);
        }
    }
}
