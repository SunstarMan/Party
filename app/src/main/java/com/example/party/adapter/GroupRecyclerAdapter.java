package com.example.party.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.party.R;
import com.example.party.bean.User;

import java.util.List;

public class GroupRecyclerAdapter extends RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder> {
    private List<User> groupMembers;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textUsername;
        private TextView textAccount;
        private TextView textIdentity;
        private TextView textBranch;
        private TextView textMotto;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUsername = itemView.findViewById(R.id.item_group_member_text_username);
            textAccount = itemView.findViewById(R.id.item_group_member_text_account);
            textIdentity = itemView.findViewById(R.id.item_group_member_text_identity);
            textBranch = itemView.findViewById(R.id.item_group_member_text_branch);
            textMotto = itemView.findViewById(R.id.item_group_member_text_motto);
        }
    }

    public GroupRecyclerAdapter(List<User> groupMembers) {
        this.groupMembers = groupMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = groupMembers.get(position);
        holder.textUsername.setText(user.getUsername());
        holder.textAccount.setText(user.getAccount());
        holder.textIdentity.setText(user.getIdentity());
        holder.textBranch.setText(user.getBranch());
        holder.textMotto.setText(user.getMotto());
    }

    @Override
    public int getItemCount() {
        return groupMembers.size();
    }
}
