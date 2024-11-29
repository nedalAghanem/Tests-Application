package com.example.homework1exam;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1exam.database.DatabaseAccess;
import com.example.homework1exam.database.Question;
import com.example.homework1exam.database.Result;
import com.example.homework1exam.databinding.CustomQustionItemBinding;

import java.util.ArrayList;
import java.util.Random;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder> {
    ArrayList<Question> questions;
    Context context ;
    private int numberOfQuestions = 0 ;
    int userID ;

    private final int[] colors = {
            // from Internet
            Color.parseColor("#FFCDD2"), // وردي
            Color.parseColor("#C8E6C9"), // اخضر
            Color.parseColor("#BBDEFB"), // ازرق
            Color.parseColor("#FFF9C4"), // اصفر
            Color.parseColor("#D1C4E9")  // بنفسجي
    };


    public QuestionsAdapter(ArrayList<Question> questions, Context context , int userId) {
        this.questions = questions;
        this.context = context;
        this.userID = userId ;
    }

    @NonNull
    @Override
    public QuestionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionsViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.custom_qustion_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsViewHolder holder, int position) {
        Question question = questions.get(position);

        holder.bind(question);

        holder.binding.customIvStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.binding.customIvStar.getDrawable().getConstantState() ==
                        context.getResources().getDrawable(R.drawable.star).getConstantState()) {
                    holder.binding.customIvStar.setImageResource(R.drawable.star_selected);
                } else {
                    holder.binding.customIvStar.setImageResource(R.drawable.star);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder{
        CustomQustionItemBinding binding ;
        Question question ;
        private DatabaseAccess db;


        public QuestionsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomQustionItemBinding.bind(itemView);
        }
        // this method to clean Code
        public void bind(Question question) {
            this.question = question;
            binding.customQustionText.setText(question.getQuestionText());
            binding.customChoise1.setText(question.getOptionA());
            binding.customChoise2.setText(question.getOptionB());
            binding.customChoise3.setText(question.getOptionC());
            binding.customChoise4.setText(question.getOptionD());
            numberOfQuestions += 1;
            binding.customTvNumberQuestion.setText(String.valueOf(numberOfQuestions));

            // تعيين لون عشوائي للكارد فيو
            int randomColor = colors[new Random().nextInt(colors.length)];
            binding.cardView.setCardBackgroundColor(randomColor);

            // add a new answer
            // to add a new answer
            db = DatabaseAccess.getInstance(context.getApplicationContext());


            binding.customRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Intent intent = new Intent(context.getApplicationContext(), SoundService.class);
                    db.open();
                    if (checkedId == R.id.custom_choise1) {
                        // choise A
                        addAnswerToDatabase(userID,question.getQuestionId(),"A",question.getCorrectOption());
                        context.stopService(intent);
                        context.startService(intent);
                    } else if (checkedId == R.id.custom_choise2) {
                        // choise B
                        addAnswerToDatabase(userID,question.getQuestionId(),"B",question.getCorrectOption());
                        context.stopService(intent);
                        context.startService(intent);
                    } else if (checkedId == R.id.custom_choise3) {
                        // choise C
                        addAnswerToDatabase(userID,question.getQuestionId(),"C",question.getCorrectOption());
                        context.stopService(intent);
                        context.startService(intent);
                    } else if (checkedId == R.id.custom_choise4) {
                        // choise D
                        addAnswerToDatabase(userID,question.getQuestionId(),"D",question.getCorrectOption());
                        context.stopService(intent);
                        context.startService(intent);
                    }
                    db.close();

                }
            });

        }
            /////
                // Add A data to Answer Table
            private void addAnswerToDatabase(int userId, int questionId, String userAnswer,String correctOption) {
                if (db.isAnswerExists(userId, questionId)) {
                    // Update The Answer
                    int re = db.updateAnswer(userId, questionId, userAnswer,correctOption);
                    Toast.makeText(context.getApplicationContext(), "Updated a answer "+"userId:"+userID, Toast.LENGTH_SHORT).show();
                } else {
                    // Add a new Answer
                    long re = db.insertUserAnswer(userId, questionId, userAnswer, correctOption);
                    Toast.makeText(context.getApplicationContext(), "Add answer Sucssesfuly ! "+"userId:"+userID, Toast.LENGTH_SHORT).show();
                }
            }



    }


}

