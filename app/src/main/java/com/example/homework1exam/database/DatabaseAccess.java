package com.example.homework1exam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new QuestionsDatabase(context);
    }

    // Singleton
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        if (this.database == null || !this.database.isOpen()) {
            this.database = this.openHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

//    //// All Questions
//    public ArrayList<Question> getAllQuestions() {
//        ArrayList<Question> questions = new ArrayList<>();
//
//        Cursor cursor = database.rawQuery("SELECT question_id, question_text, option_a, option_b, option_c, option_d FROM Questions", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int questionId = cursor.getInt(cursor.getColumnIndex("question_id"));
//                String questionText = cursor.getString(cursor.getColumnIndex("question_text"));
//                String optionA = cursor.getString(cursor.getColumnIndex("option_a"));
//                String optionB = cursor.getString(cursor.getColumnIndex("option_b"));
//                String optionC = cursor.getString(cursor.getColumnIndex("option_c"));
//                String optionD = cursor.getString(cursor.getColumnIndex("option_d"));
//
//                Question question = new Question(questionId, questionText, optionA, optionB, optionC, optionD, null);
//                questions.add(question);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        database.close();
//        // show As randomly
//        Collections.shuffle(questions);
//
//        return questions;
//    }

        // Show 30 Question randomly
        public ArrayList<Question> getAllQuestions() {
            ArrayList<Question> questions = new ArrayList<>();

            String selectQuery = "SELECT * FROM Questions ORDER BY RANDOM() LIMIT 30";
            Cursor cursor = database.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("question_id"));
                    String questionText = cursor.getString(cursor.getColumnIndex("question_text"));
                    String optionA = cursor.getString(cursor.getColumnIndex("option_a"));
                    String optionB = cursor.getString(cursor.getColumnIndex("option_b"));
                    String optionC = cursor.getString(cursor.getColumnIndex("option_c"));
                    String optionD = cursor.getString(cursor.getColumnIndex("option_d"));
                    String correctOption = cursor.getString(cursor.getColumnIndex("correct_option"));

                    // إضافة الكائن إلى القائمة
                    questions.add(new Question(id, questionText, optionA, optionB, optionC, optionD, correctOption));
                } while (cursor.moveToNext());
            }

            // إغلاق المؤشر وقاعدة البيانات
            cursor.close();
            database.close();

            return questions;
        }




    //// Add Option Selection of User
    public long insertUserAnswer(int userId, int questionId, String userAnswer, String correctOption) {
//        String isCorrect = userAnswer.equals(correctOption) ? "Yes" : "No";
        String isCorrect;
        if (userAnswer.equals(correctOption)) {
            isCorrect = "Yes";
        } else {
            isCorrect = "No";
        }
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("question_id", questionId);
        values.put("user_answer", userAnswer);
        values.put("is_correct", isCorrect);

        long re = database.insert("Results", null, values);
        database.close();
        return re;
    }
    // دالة لتحديث الإجابة الحالية
    public int updateAnswer(int userId, int questionId, String userAnswer,String correctOption) {
        ContentValues values = new ContentValues();
        values.put("user_answer", userAnswer);
        int re =database.update("Results", values, "user_id = ? AND question_id = ?", new String[]{String.valueOf(userId), String.valueOf(questionId)});
        database.close();
        return re;
    }
//    // دالة للتحقق مما إذا كانت الإجابة موجودة
public boolean isAnswerExists(int userId, int questionId) {
    // هنا يمكنك استخدام استعلام للتحقق مما إذا كانت الإجابة موجودة بالفعل
    String query = "SELECT COUNT(*) FROM Results WHERE user_id = ? AND question_id = ?";
    Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(questionId)});
    boolean exists = false;
    if (cursor != null) {
        if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
            exists = true;
        }
        cursor.close();
    }
    return exists;
}

    //// Show Result of Users
    public List<UserScore> getUsersScores() {
        List<UserScore> scoresList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT Users.user_name,User.user_email, COUNT(Results.is_correct) AS score " +
                "FROM Users " +
                "JOIN Results ON Users.user_id = Results.user_id " +
                "WHERE Results.is_correct = 'Yes' " +
                "GROUP BY Users.user_id", null);

        if (cursor.moveToFirst()) {
            do {
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String userEmail = cursor.getString(cursor.getColumnIndex("user_email"));
                int score = cursor.getInt(cursor.getColumnIndex("score"));

                UserScore userScore = new UserScore(userName,userEmail, score);
                scoresList.add(userScore);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();

        return scoresList;
    }


//    //// to search in a Users
//    public UserScore searchUserByUserName(String userName) {
//        UserScore userScore = null;
//
//        String query = "SELECT Users.user_name, COUNT(Results.is_correct) AS score " +
//                "FROM Users " +
//                "LEFT JOIN Results ON Users.user_id = Results.user_id " +
//                "WHERE Users.user_name LIKE ? " +
//                "GROUP BY Users.user_id";
//
//        Cursor cursor = database.rawQuery(query, new String[]{"%" + userName + "%"});
//
//        if (cursor != null && cursor.moveToFirst()) {
//            String name = cursor.getString(cursor.getColumnIndex("user_name"));
//            int score = cursor.getInt(cursor.getColumnIndex("score"));
//
//            userScore = new UserScore(name, score);
//        }
//
//        if (cursor != null) {
//            cursor.close();
//        }
//        database.close();
//
//        return userScore;
//    }
    //// add a new User
    public void addUser(String userName, String userEmail) {

        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        values.put("user_email", userEmail);

        database.insert("Users", null, values);
        database.close();
    }

    // To return user_id from Register Activity
    public int returnUserId(String userName){
        // Query
        open();
        String query = "SELECT Users.user_id FROM Users WHERE Users.user_name = ? ";
        Cursor cursor = null;
        int userId = -1 ;
        try {
        cursor = database.rawQuery(query, new String[]{ userName });

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("user_id")) ;

        }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userId ;
    }

    // to Return the userName ,email and score
    public UserScore getUserScore(int userId) {
        UserScore userScore = null;
        open();
        String query = "SELECT u.user_name, u.user_email, COUNT(r.is_correct) AS correct_answers " +
                "FROM Users u " +
                "LEFT JOIN Results r ON u.user_id = r.user_id " +
                "WHERE r.is_correct = 'Yes' AND u.user_id = ? " +
                "GROUP BY u.user_id";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.moveToFirst()) {
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String userEmail = cursor.getString(cursor.getColumnIndex("user_email"));
            int correctAnswers = cursor.getInt(cursor.getColumnIndex("correct_answers"));

            userScore = new UserScore(userName, userEmail, correctAnswers);
            cursor.close();
        }
        return userScore;
    }



}
