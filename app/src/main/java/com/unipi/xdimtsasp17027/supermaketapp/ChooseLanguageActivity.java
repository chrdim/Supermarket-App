package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseLanguageActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final int REC_RESULT = 653;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();
    }

    public void setGreekLanguage(View view){
        editor.putString("language","greek");
        editor.apply();
        startActivity(new Intent(getApplicationContext(),WelcomePage.class));
    }

    public void setEnglishLanguage(View view){
        editor.putString("language","english");
        editor.apply();
        startActivity(new Intent(getApplicationContext(),WelcomePage.class));
    }


    public void microphoneClicked(View view){

         new AlertDialog.Builder(this)
        .setMessage("Πείτε 'greek' για επιλογή ελληνικής γλώσσας ή πείτε 'english' για επιλογή αγγλικής γλώσσας."+"\n"+"Say 'greek' to select greek language or say 'english' to select english language.")

        .setCancelable(true).setNegativeButton("X", null)
        .setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Σας ακούω\nI hear you");

                        startActivityForResult(intent, REC_RESULT);


                    }
                }).create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REC_RESULT && resultCode == Activity.RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches.contains("Greek")) {
                editor.putString("language","greek");
                editor.apply();
                startActivity(new Intent(getApplicationContext(),WelcomePage.class));

            } else if (matches.contains("English")) {
                editor.putString("language","english");
                editor.apply();
                startActivity(new Intent(getApplicationContext(),WelcomePage.class));
            }else{
                 new AlertDialog.Builder(this)
                .setMessage("Η εντολή που δώσατε δεν υποστηρίστηκε\nΤhe command you gave was not recognized")
                .setNegativeButton("X", null)
                .setCancelable(true)
                .setPositiveButton("Πάλι-Rentry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Σας ακούω\nI hear you");

                        startActivityForResult(intent, REC_RESULT);
                    }
                }).create().show();

            }
        }
    }
    @Override
    public void onBackPressed() {

        finishAffinity();

    }
}