package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterCustomerEmailNamePhone extends AppCompatActivity {

    EditText emailAddressEditText,usernameEditText,phoneEditText;
    SharedPreferences preferences;
    Button continueButton;
    SharedPreferences.Editor editor;
    TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer_email_name_phone);

        emailAddressEditText=findViewById(R.id.emailAddressEditText2);
        usernameEditText=findViewById(R.id.usernameEditText);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        continueButton=findViewById(R.id.continueButton);

        phoneEditText=findViewById(R.id.editTextPhone);

        editor=preferences.edit();


        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView7);
            textView1.setText("Also,");
            textView2=findViewById(R.id.textView8);
            textView2.setText("Insert your email,uesername and phone number");

            usernameEditText.setHint("Username");
            phoneEditText.setHint("Phone");

            continueButton.setText("Submit");

        }
    }

    public void saveEmailName(View view){
        if(!(emailAddressEditText.getText().toString().equals("")||usernameEditText.getText().toString().equals("")||phoneEditText.getText().toString().equals(""))){
            if(Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString()).matches()){
                editor.putString("email",emailAddressEditText.getText().toString());
                editor.putString("username",usernameEditText.getText().toString());
                editor.putString("phone",phoneEditText.getText().toString());
                editor.apply();
                startActivity(new Intent(this, RegisterPassword.class));
            }else{
                if(preferences.getString("language","").equals("english")){
                    Toast.makeText(getApplicationContext(),"The email form is not acceptable",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Η μορφή του email δεν είναι συμβατή",Toast.LENGTH_SHORT).show();
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




        if(usernameEditText.getText().toString().equals("")&&emailAddressEditText.getText().toString().equals("")){
            finish();
            startActivity(new Intent(getApplicationContext(),Register.class));
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
                                    startActivity(new Intent(getApplicationContext(),Register.class));


                                }
                            }).create().show();
        }



    }


}