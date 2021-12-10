package com.abhinav.quiz.Model;

public class QuestionModel {


    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int correctAns;
    private String difficulty;


    public QuestionModel(){}

    public QuestionModel(String question, String option1, String option2, String option3, int correctAns,String difficulty) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctAns = correctAns;
        this.difficulty = difficulty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static String[] getAllDifficulty(){
        String[] array = new String[3];
        array[0] = DIFFICULTY_EASY;
        array[1] = DIFFICULTY_MEDIUM;
        array[2] = DIFFICULTY_HARD;

        return array;
    }

}
