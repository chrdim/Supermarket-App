package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterAdminTimeAndMinimumOrder extends AppCompatActivity {

    EditText timeEditText,minimumOrderEditText;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    TextView textView1,textView2;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin_time_and_minimum_order);

        timeEditText=findViewById(R.id.editTextDeliveryTime);
        minimumOrderEditText=findViewById(R.id.editTextMinimumOrder);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();


        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView7232);
            textView1.setText("And,");
            textView2=findViewById(R.id.textView8222);
            textView2.setText("insert the minimum order and the delivery time");


            continueButton=findViewById(R.id.saveButton24);
            continueButton.setText("Submit");

            timeEditText.setHint("Delivery time");
            minimumOrderEditText.setHint("Minimum order");

        }

    }

    public void save(View view){

        if(!(timeEditText.getText().toString().equals("")|| minimumOrderEditText.getText().toString().equals(""))){

            editor.putString("time",timeEditText.getText().toString());
            editor.putString("minimumorder",minimumOrderEditText.getText().toString());
            editor.apply();
            startActivity(new Intent(getApplicationContext(),RegisterAdminImage.class));

        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"Please fill out all field to continue",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Συμπληρώστε όλα τα πεδία για να συνεχίσετε",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {


        if (timeEditText.getText().toString().equals("") && minimumOrderEditText.getText().toString().equals("")) {
            finish();
            startActivity(new Intent(getApplicationContext(), Register.class));
        } else {

            String message;
            String title;
            String positive;
            String negative;
            if(preferences.getString("language","").equals("english")){
                message="Your inputs will be lost";
                title="Caution";
                negative="Cancel";
                positive="Back";
            }else {
                message="Τα δεδομενα θα χαθουν";
                title="ΠΡΟΣΟΧΗ";
                negative="Άκυρο";
                positive="Πίσω";
            }
            new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setTitle(title)
                    .setCancelable(true)
                    .setNegativeButton(negative, null)
                    .setPositiveButton(positive,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), RegisterPassword.class));


                                }
                            }).create().show();
        }
    }
}