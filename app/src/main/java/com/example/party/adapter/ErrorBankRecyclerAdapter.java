package com.example.party.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.party.R;
import com.example.party.bean.Question;

import java.util.List;

public class ErrorBankRecyclerAdapter extends RecyclerView.Adapter<ErrorBankRecyclerAdapter.ViewHolder> {
    private List<Question> questions;

    public ErrorBankRecyclerAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_error_bank_box, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.textStem.setText(question.getId() + "、" + question.getQuestion());
        holder.textOptionA.setText("A、" + question.getOptionA());
        holder.textOptionB.setText("B、" + question.getOptionB());
        holder.textOptionC.setText("C、" + question.getOptionC());
        holder.textOptionD.setText("D、" + question.getOptionD());
        holder.textAnswer.setText("答案：" + question.getAnswer());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textStem;
        private TextView textOptionA;
        private TextView textOptionB;
        private TextView textOptionC;
        private TextView textOptionD;
        private TextView textAnswer;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textStem = itemView.findViewById(R.id.item_error_bank_box_stem);
            textOptionA = itemView.findViewById(R.id.item_error_bank_box_text_optionA);
            textOptionB = itemView.findViewById(R.id.item_error_bank_box_text_optionB);
            textOptionC = itemView.findViewById(R.id.item_error_bank_box_text_optionC);
            textOptionD = itemView.findViewById(R.id.item_error_bank_box_text_optionD);
            textAnswer = itemView.findViewById(R.id.item_error_bank_box_text_answer);
        }
    }
}
