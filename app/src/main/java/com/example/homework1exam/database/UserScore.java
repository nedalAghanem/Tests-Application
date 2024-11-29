package com.example.homework1exam.database;

public class UserScore {
    private String userName;
    private String userEmail;
    private int correctAnswers;

    public UserScore(String userName, String userEmail, int correctAnswers) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.correctAnswers = correctAnswers;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }
}
