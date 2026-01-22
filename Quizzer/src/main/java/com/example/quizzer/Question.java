package com.example.quizzer;

public class Question {

    private String question;
    private String[] answers;
    private int correct_answer;

    public Question(String question, String[] answers, int correct_answer) {
        this.question = question;
        this.answers = answers;
        this.correct_answer = correct_answer;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrect_answer() {
        return correct_answer;
    }
}





