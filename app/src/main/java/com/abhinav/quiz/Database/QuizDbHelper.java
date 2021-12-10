package com.abhinav.quiz.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.abhinav.quiz.Model.QuestionModel;
import com.abhinav.quiz.Database.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 5;
    private SQLiteDatabase db;
    public QuizDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NO + " INTEGER," +
                QuestionsTable.COLUMN_DIFFIUCULTY + " TEXT " +
                ")";


        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        addQuestionsToTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void addQuestionsToTable() {

        //Easy questions
        QuestionModel questionObject1 = new QuestionModel("National Sports Day(NSD) is celebrated on which date in India?","August 28","August 29","August 26",2,QuestionModel.DIFFICULTY_EASY);
        addQuestion(questionObject1);

        QuestionModel questionObject2 = new QuestionModel("Durand Cup is associated with?","Swimming","Table Tennis","Football",3,QuestionModel.DIFFICULTY_EASY);
        addQuestion(questionObject2);

        QuestionModel questionObject3 = new QuestionModel("Cannes Film Festival is held in ?","Italy","France","Germany",1,QuestionModel.DIFFICULTY_EASY);
        addQuestion(questionObject3);

        QuestionModel questionObject4 = new QuestionModel("Who among the following won the Oscar 2021 for ‘Best Actor in a Leading role’?","Gary Oldman","Anthony Hopkins","Chadwick Boseman",2,QuestionModel.DIFFICULTY_EASY);
        addQuestion(questionObject4);

        QuestionModel questionObject5 = new QuestionModel("In which year, Sikkim become a part of India","1972","1977","1975",3,QuestionModel.DIFFICULTY_EASY);
        addQuestion(questionObject5);

        //medium questions
        QuestionModel questionObject6 = new QuestionModel("Which among the following is not a trophy or cup related to Hockey?","Narang Cup","Bombay Gold Cup","Indira Gold Cup",1,QuestionModel.DIFFICULTY_MEDIUM);
        addQuestion(questionObject6);

        QuestionModel questionObject7 = new QuestionModel("In which of the following sports Decision Referral System is used?","Football","Hockey","Cricket",3,QuestionModel.DIFFICULTY_MEDIUM);
        addQuestion(questionObject7);

        //hard questions
        QuestionModel questionObject8 = new QuestionModel("Birdie and Eagle are 2 terms related to which of the following sports?","Polo","Golf","Billiards",2,QuestionModel.DIFFICULTY_MEDIUM);
        addQuestion(questionObject8);

        QuestionModel questionObject9 = new QuestionModel("Who is the President of Hockey India?","Mohammad Mushtaq Ahmed","Tapan Kumar Das","Rajinder Singh",1,QuestionModel.DIFFICULTY_MEDIUM);
        addQuestion(questionObject9);

        QuestionModel questionObject10 = new QuestionModel("Who among the following played the leading lady in the film ‘Mission Mangal’ that tells the dramatic true story of the women behind India’s first mission to Mars?","Kareena Kapoor","Vidya Balan","Kajol",2,QuestionModel.DIFFICULTY_MEDIUM);
        addQuestion(questionObject10);

        QuestionModel questionObject11 = new QuestionModel("Velodrome is an arena for which among the following sporting events?","Track Cycling","Tennis","Ice Hockey",1,QuestionModel.DIFFICULTY_HARD);
        addQuestion(questionObject11);

        QuestionModel questionObject12 = new QuestionModel("What was the theme of the 2016 Indian Film Festival of Melbourne(IFFM) at which Sonam Kapoor won the Best Actress Award for her performance in Neerja Bhanot’s biopic ‘Neerja’?","Enrichment of Women Status","Celebration of Life","Female Empowerment",3,QuestionModel.DIFFICULTY_HARD);
        addQuestion(questionObject12);

        QuestionModel questionObject13 = new QuestionModel("Who can be the Chair person of National Human Rights Commission in India?","Chief Justice of High Court ","Chief Justice of Supreme Court ","Judge of Supreme Court ",2,QuestionModel.DIFFICULTY_HARD);
        addQuestion(questionObject13);

        QuestionModel questionObject14 = new QuestionModel("The Union Public Service Commission of India has been established under the Article","Article 178","Article 272","Article 315",3,QuestionModel.DIFFICULTY_HARD);
        addQuestion(questionObject14);

        QuestionModel questionObject15 = new QuestionModel("The National Human Rights Commission was formed in the year?","1993","1990","1989",1,QuestionModel.DIFFICULTY_HARD);
        addQuestion(questionObject15);

    }

    public void addQuestion(QuestionModel questionObject){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION,questionObject.getQuestion());
        contentValues.put(QuestionsTable.COLUMN_OPTION1,questionObject.getOption1());
        contentValues.put(QuestionsTable.COLUMN_OPTION2,questionObject.getOption2());
        contentValues.put(QuestionsTable.COLUMN_OPTION3,questionObject.getOption3());
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NO,questionObject.getCorrectAns());
        contentValues.put(QuestionsTable.COLUMN_DIFFIUCULTY,questionObject.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME,null,contentValues);
    }




//    public List<QuestionModel> getAllQuestions(){
//        List<QuestionModel> questionModelArrayList = new ArrayList<>();
//        db = getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME,null);
//        if(c.moveToFirst()){
//            do{
//                QuestionModel questionModel = new QuestionModel();
//                questionModel.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
//                questionModel.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
//                questionModel.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
//                questionModel.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
//                questionModel.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
//                questionModel.setCorrectAns(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NO)));
//                questionModel.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFIUCULTY)));
//                questionModelArrayList.add(questionModel);
//
//            }while (c.moveToNext());
//        }
//        c.close();
//        return questionModelArrayList;
//    }

    public List<QuestionModel> getQuestionsWithDifficulty(String difficulty){
        List<QuestionModel> questionModelArrayList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFIUCULTY + " = ?", selectionArgs);
        if(c.moveToFirst()){
            do{
                QuestionModel questionModel = new QuestionModel();
                questionModel.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                questionModel.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                questionModel.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                questionModel.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                questionModel.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                questionModel.setCorrectAns(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NO)));
                questionModel.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFIUCULTY)));
                questionModelArrayList.add(questionModel);

            }while (c.moveToNext());
        }
        c.close();
        return questionModelArrayList;
    }

}
