package com.example.homework1exam.database;

public class Result {

    private int resultId;
    private int userId;
    private int questionId;
    private String userAnswer;
    private String isCorrect;

    public Result(int resultId, int userId, int questionId, String userAnswer, String isCorrect) {
        this.resultId = resultId;
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }

    public Result(int userId, int questionId, String userAnswer, String isCorrect) {
        this.userId = userId;
        this.questionId = questionId;
        this.userAnswer = userAnswer;
        this.isCorrect = isCorrect;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }
}
