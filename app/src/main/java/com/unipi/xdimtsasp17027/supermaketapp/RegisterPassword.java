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

public class RegisterPassword extends AppCompatActivity {

    EditText passwordEditText,passwordVerificationEditText;
    Button continueButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);

        passwordEditText=findViewById(R.id.passEditText2);
        passwordVerificationEditText=findViewById(R.id.passEditText3);
        continueButton=findViewById(R.id.continueButton2);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView9);
            textView1.setText("Next,");
            textView2=findViewById(R.id.textView10);
            textView2.setText("Choose your password");

            passwordEditText.setHint("Password");
            passwordVerificationEditText.setHint("Validation");

            continueButton.setText("Submit");

        }
    }

    public void savePassword(View view){

        if(!(passwordEditText.getText().toString().equals("") || passwordVerificationEditText.getText().toString().equals(""))){
            if(passwordEditText.getText().toString().length()>=6){
                if(passwordEditText.getText().toString().equals(passwordVerificationEditText.getText().toString())){
                    editor.putString("password",passwordEditText.getText().toString());
                    editor.apply();
                    if(preferences.getString("userProperty","").equals("customer")){
                        startActivity(new Intent(this,ConfirmCustomerActivity.class));
                    }else if(preferences.getString("userProperty","").equals("admin")){
                        startActivity(new Intent(this,RegisterAdminTimeAndMinimumOrder.class));
                    }
                }else{
                    if(preferences.getString("language","").equals("english")){
                        Toast.makeText(getApplicationContext(),"Not same passwords",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Οι κωδικοί δεν ταιριάζουν",Toast.LENGTH_SHORT).show();
                    }
                }

            }else{
                if(preferences.getString("language","").equals("english")){
                    Toast.makeText(getApplicationContext(),"The password must contain minimum 6 characters",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Ο κωδικός θα πρέπει να περιέχει τουλάχιστον 6 χαρακτήρες",Toast.LENGTH_SHORT).show();
                }
            }

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




        if(passwordEditText.getText().toString().equals("")){
            finish();
            if(preferences.getString("userProperty","").equals("customer")){
                startActivity(new Intent(this, RegisterCustomerEmailNamePhone.class));
            }else if(preferences.getString("userProperty","").equals("admin")){
                startActivity(new Intent(this,RegisterAdminEmailName.class));
            }

        }else{
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
                                    if(preferences.getString("userProperty","").equals("customer")){
                                        startActivity(new Intent(getApplicationContext(), RegisterCustomerEmailNamePhone.class));
                                    }else if(preferences.getString("userProperty","").equals("admin")){
                                        startActivity(new Intent(getApplicationContext(),RegisterAdminEmailName.class));
                                    }


                                }
                            }).create().show();
        }



    }

}