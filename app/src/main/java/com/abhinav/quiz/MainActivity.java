package com.abhinav.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import com.abhinav.quiz.Model.QuestionModel;

public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE = 1;
    public static String EXTRA_DIFFICULTY = "extraDifficulty";
    String SHARED_PREFS = "sharedprefs";
    String KEY_HIGHSCORE = "keyhighscore";

    private TextView textView_highscore;
    private Spinner spinner;
    private int highscore;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Button startQuizButton = findViewById(R.id.Start_button);
        Button resetScoreButton = findViewById(R.id.Reset_button);
        builder = new AlertDialog.Builder(this);
        textView_highscore = findViewById(R.id.HighScore_textView);
        spinner = findViewById(R.id.Choose_difficluty_spinner);

        String[] diff_levels = QuestionModel.getAllDifficulty();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item,diff_levels);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        loadHighScore();

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizActivity();
            }
        });

        resetScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to reset the highscore?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                                SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                                highscore = prefs.getInt(KEY_HIGHSCORE,0);
                                if(highscore > 0){
                                    highscore = 0;
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putInt(KEY_HIGHSCORE,highscore);
                                    editor.apply();
                                }
                                textView_highscore.setText("Highscore: " + highscore);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("Reset");
                alertDialog.show();
            }
        });

    }

    private void startQuizActivity(){
        String difficulty = spinner.getSelectedItem().toString();
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra(EXTRA_DIFFICULTY,difficulty);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if(score > highscore){
                    highscore = score;
                    textView_highscore.setText("Highscore: " + highscore);
                    SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(KEY_HIGHSCORE,highscore);
                    editor.apply();
                }
            }
        }
    }

    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE,0);
        textView_highscore.setText("Highscore: " + highscore);
    }
}