package com.example.homework1exam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class QuestionsDatabase extends SQLiteAssetHelper {

    Context context ;
    static final String DB_NAME = "Question.db";

    static final String DB_TABLE_QUESTIONS = "Questions";
    static final String DB_COLLUMN_QUESTION_ID = "question_id";
    static final String DB_COLLUMN_QUESTION_TEXT = "question_text";
    static final String DB_COLLUMN_QUESTION_OPTION_A = "option_a";
    static final String DB_COLLUMN_QUESTION_OPTION_B = "option_b";
    static final String DB_COLLUMN_QUESTION_OPTION_C = "option_c";
    static final String DB_COLLUMN_QUESTION_OPTION_D = "option_d";
    static final String DB_COLLUMN_QUESTION_CORRECT_OPTION = "correct_option";

    static final String DB_TABLE_RESULTS = "Results";
    static final String DB_COLLUMN_RESULT_ID = "result_id";
    static final String DB_COLLUMN_RESULT_USER_ID = "user_id";
    static final String DB_COLLUMN_RESULT_QUESTION_ID = "question_id";
    static final String DB_COLLUMN_RESULT_USER_ANSWER = "user_answer";
    static final String DB_COLLUMN_RESULT_IS_CORRECT = "is_correct";

    static final String DB_TABLE_USERS = "Users";
    static final String DB_COLLUMN_USER_ID = "user_id";
    static final String DB_COLLUMN_USER_NAME = "user_name";
    static final String DB_COLLUMN_USER_EMAIL = "user_email";

    static final int DB_VERSION = 1 ;


    public QuestionsDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE "+DB_TABLE_NAME+" ADD mDate TEXT");
//        // هنا نقوم بإنشاء نسخة من الجدول القديم بدون user_id
//        db.execSQL("CREATE TABLE temp_users (user_name TEXT NOT NULL, user_email TEXT);");
//
//        // نسخ البيانات القديمة إلى الجدول الجديد
//        db.execSQL("INSERT INTO temp_users (user_name, user_email) SELECT user_name, user_email FROM Users;");
//
//        // حذف الجدول القديم
//        db.execSQL("DROP TABLE IF EXISTS Users;");
//
//        // إعادة تسمية الجدول المؤقت إلى الجدول الأصلي
//        db.execSQL("ALTER TABLE temp_users RENAME TO Users;");
//
//        // إعادة إنشاء الجدول مع user_id auto-increment
//        db.execSQL("CREATE TABLE Users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_name TEXT NOT NULL, user_email TEXT);");
//
//        // نسخ البيانات من الجدول المؤقت إلى الجدول الجديد (سيتم توليد user_id تلقائياً)
//        db.execSQL("INSERT INTO Users (user_name, user_email) SELECT user_name, user_email FROM temp_users;");
//
//        // حذف الجدول المؤقت
//        db.execSQL("DROP TABLE IF EXISTS temp_users;");
    }
}
