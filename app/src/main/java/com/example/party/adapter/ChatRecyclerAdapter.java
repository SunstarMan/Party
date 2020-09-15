package com.example.party.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.party.R;
import com.example.party.bean.ChatMessage;

import java.util.List;

public class ChatRecyclerAdapter extends RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder> {
    private List<ChatMessage> chatMessages;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textReceive;
        private TextView textSend;
        private LinearLayout linearReceive;
        private LinearLayout linearSend;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textReceive = itemView.findViewById(R.id.item_chat_box_text_receive);
            textSend = itemView.findViewById(R.id.item_chat_box_text_send);
            linearReceive = itemView.findViewById(R.id.item_chat_box_linear_receive);
            linearSend = itemView.findViewById(R.id.item_chat_box_linear_send);
        }
    }

    public ChatRecyclerAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerAdapter.ViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        if (chatMessage.getType() == ChatMessage.RECEIVE) {
            holder.linearReceive.setVisibility(View.VISIBLE);
            holder.linearSend.setVisibility(View.GONE);
            holder.textReceive.setText(chatMessage.getMessage());
        } else {
            holder.linearSend.setVisibility(View.VISIBLE);
            holder.linearReceive.setVisibility(View.GONE);
            holder.textSend.setText(chatMessage.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }
}
