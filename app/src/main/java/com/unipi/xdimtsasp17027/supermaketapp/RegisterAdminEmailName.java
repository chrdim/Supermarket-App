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

public class RegisterAdminEmailName extends AppCompatActivity {

    EditText emailAddressEditText,storenameEditText;
    SharedPreferences preferences;
    Button continueButton;
    SharedPreferences.Editor editor;
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin_email_name);

        emailAddressEditText=findViewById(R.id.emailAddressEditText3);
        storenameEditText=findViewById(R.id.storeNameEditText);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        continueButton=findViewById(R.id.continueButton4);

        editor=preferences.edit();


        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView13);
            textView1.setText("Then,");
            textView2=findViewById(R.id.textView14);
            textView2.setText("insert the email and the store name");


            continueButton.setText("Submit");

            storenameEditText.setHint("Store name");

        }
    }

    public void saveEmailName(View view){
        if(!(emailAddressEditText.getText().toString().equals("")||storenameEditText.getText().toString().equals(""))){
            if(Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString()).matches()){
                editor.putString("email",emailAddressEditText.getText().toString());
                editor.putString("storename",storenameEditText.getText().toString());
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

        finish();
        startActivity(new Intent(getApplicationContext(),Register.class));
    }
}