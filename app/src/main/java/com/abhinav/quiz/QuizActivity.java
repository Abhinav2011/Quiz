package com.abhinav.quiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.abhinav.quiz.Database.QuizDbHelper;
import com.abhinav.quiz.Model.QuestionModel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {
    public static String EXTRA_SCORE = "extrascore";
    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewDifficulty;
    private TextView textViewCountDown;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private Button confirmButton;



    private List<QuestionModel> questionModelArrayList;
    private int questionCount;
    private int questionCountTotal;
    private QuestionModel currentQuestion;
    private ColorStateList textColorDefault;
    private int score;
    private boolean checked = false;

    private CountDownTimer countDownTimer;
    private long timeLeft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.Question_textview);
        textViewScore = findViewById(R.id.Score_textview);
        textViewQuestionCount = findViewById(R.id.Count_questions_textview);
        textViewDifficulty = findViewById(R.id.Difficulty_textview);
        textViewCountDown = findViewById(R.id.Time_textview);
        radioGroup = findViewById(R.id.Radio_group);
        radioButton1 = findViewById(R.id.Radio_button_1);
        radioButton2 = findViewById(R.id.Radio_button_2);
        radioButton3 = findViewById(R.id.Radio_button_3);
        confirmButton = findViewById(R.id.Confirm_button);

        textColorDefault = radioButton1.getTextColors();
        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY);
        textViewDifficulty.setText("Difficulty: " + difficulty);

        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionModelArrayList = dbHelper.getQuestionsWithDifficulty(difficulty);
        questionCountTotal = questionModelArrayList.size();
        Collections.shuffle(questionModelArrayList);
        showNextQuestion();
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checked){
                    if(radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked()){
                        checkAnswerCorrect();
                    }
                    else{
                        Toast.makeText(QuizActivity.this,"You must select an answer",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                   showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion() {
        radioButton1.setTextColor(textColorDefault);
        radioButton2.setTextColor(textColorDefault);
        radioButton3.setTextColor(textColorDefault);
        radioGroup.clearCheck();

        if(questionCount < questionCountTotal){
            currentQuestion = questionModelArrayList.get(questionCount);
            textViewQuestion.setText(currentQuestion.getQuestion());
            radioButton1.setText(currentQuestion.getOption1());
            radioButton2.setText(currentQuestion.getOption2());
            radioButton3.setText(currentQuestion.getOption3());
            questionCount++;
            textViewQuestionCount.setText("Question: " + questionCount + "/" + questionCountTotal);
            checked = false;
            confirmButton.setText("Confirm");

            timeLeft = 30000;
            startCountDown();
            textViewCountDown.setTextColor(Color.BLACK);
        }
        else{
            //go to mainscreen
            finishQuiz();
        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE,score);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private void checkAnswerCorrect() {
        checked = true;
        countDownTimer.cancel();
        RadioButton radioButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNumber = 1 + radioGroup.indexOfChild(radioButtonSelected);

        if(answerNumber == currentQuestion.getCorrectAns()){
            score++;
            textViewScore.setText("Score: " + score);
        }
        //show the solution now
        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);
        if(currentQuestion.getCorrectAns() == 1){
            radioButton1.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 1 is correct");
        }
        else if(currentQuestion.getCorrectAns() == 2){
            radioButton2.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 2 is correct");
        }
        else{
            radioButton3.setTextColor(Color.GREEN);
            textViewQuestion.setText("Answer 3 is correct");
        }
        if(questionCount < questionCountTotal){
            confirmButton.setText("Next");
        }
        else{
            confirmButton.setText("Finish");
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                NumberFormat f = new DecimalFormat("00");
                int minutes = (int)(timeLeft / 1000) / 60;
                int seconds = (int) (timeLeft / 1000) % 60;
              //  String time = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
                textViewCountDown.setText(f.format(minutes) + ":" + f.format(seconds));

            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                textViewCountDown.setText("00:00");
                textViewCountDown.setTextColor(Color.RED);
                checkAnswerCorrect();
            }
        }.start();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(countDownTimer != null){
//            countDownTimer.cancel();
//        }
//    }
}
