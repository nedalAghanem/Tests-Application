package com.example.homework1exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework1exam.database.DatabaseAccess;
import com.example.homework1exam.database.Question;
import com.example.homework1exam.database.UserScore;
import com.example.homework1exam.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private QuestionsAdapter adapter;
    private DatabaseAccess db;
    int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        setSupportActionBar(binding.mainToolbar);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        db = DatabaseAccess.getInstance(this);
        db.open();
        ArrayList<Question> questions = db.getAllQuestions();
        db.close();
        adapter = new QuestionsAdapter(questions, this, userId);
        binding.mainRv.setAdapter(adapter);
        binding.mainRv.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRv.setHasFixedSize(true);
        adapter.notifyDataSetChanged();


        binding.mainFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(binding.mainToolbar, "Foalt Action Button Clicked", BaseTransientBottomBar.LENGTH_LONG).show();

                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                UserScore userScore = db.getUserScore(userId);
                if (userScore != null) {
                    // استخدم المعلومات حسب الحاجة
                    String userName = userScore.getUserName();
                    String email = userScore.getUserEmail();
                    int correctAnswers = userScore.getCorrectAnswers();
                    Toast.makeText(MainActivity.this, "name:" + userName + "/email:" + email + "/score:" + correctAnswers, Toast.LENGTH_SHORT).show();
                    intent.putExtra("user_name",userName);
                    intent.putExtra("email",email);
                    intent.putExtra("score",correctAnswers);
                    startActivity(intent);
//                    finish();


                }
            }
        });

//        registerForContextMenu(tv);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_exit) {
            Toast.makeText(getApplicationContext(), "You are log out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        getMenuInflater().inflate(R.menu.menu_copytext,menu);
//    }

    // to copy aQuastion from a card
//    @Override
//    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.menu_copy){
//            Toast.makeText(getApplicationContext(), "Text Copyed", Toast.LENGTH_SHORT).show();
//            String text = tv.getText().toString();
//            return true ;
//        }
//        return false ;
//    }

}