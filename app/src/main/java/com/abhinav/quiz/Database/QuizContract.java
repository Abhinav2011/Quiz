package com.abhinav.quiz.Database;


import android.provider.BaseColumns;

//this will be container for diffrent constants for SQLite operations
public final class QuizContract {

    private QuizContract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NO = "answer_no";
        public static final String COLUMN_DIFFIUCULTY = "difficulty";


    }
}
