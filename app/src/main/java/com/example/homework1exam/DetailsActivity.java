package com.example.homework1exam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.homework1exam.database.DatabaseAccess;
import com.example.homework1exam.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
    ActivityDetailsBinding binding ;
    private DatabaseAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        Intent intent = getIntent();
        String userName = intent.getStringExtra("user_name");
        String email = intent.getStringExtra("email");
        int score = intent.getIntExtra("score",00);

//        String formattedScore = String.format("%02d", score); // "05"

        binding.detailsTvUsername.setText(userName);
        binding.detailsTvEmail.setText(email);
        binding.detailsTvUsermark.setText(score);


    }
}