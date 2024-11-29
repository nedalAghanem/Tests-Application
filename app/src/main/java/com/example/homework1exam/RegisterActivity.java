package com.example.homework1exam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.homework1exam.database.DatabaseAccess;
import com.example.homework1exam.databinding.ActivityMainBinding;
import com.example.homework1exam.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    private DatabaseAccess db;
    public static final int PERMISSION_REQ_CODE = 301 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            // هذا الكود اذا لم ياخذ الصلاحية يرجع يطلبها
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQ_CODE);
        }

        db = DatabaseAccess.getInstance(this);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.usernameInput.getText().toString().trim();
                String userEmail = binding.emailInput.getText().toString().trim();
                // add user data to database
                if (!userName.isEmpty() && !userEmail.isEmpty()) {
                    db.open();
                    db.addUser(userName, userEmail);
                    Toast.makeText(RegisterActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                    binding.usernameInput.setText("");
                    binding.emailInput.setText("");
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    int id = db.returnUserId(userName);
                    intent.putExtra("userId",id);
                    startActivity(intent);
                    finish();
                    // يمكنك هنا الانتقال إلى نشاط آخر أو مسح الحقول
                } else {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }

        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQ_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // تم الحصول على الصلاحية

            }else {
                // تم رفض الصلاحية قم بارسال رسالة للمستخدم
            }
        }
    }
}