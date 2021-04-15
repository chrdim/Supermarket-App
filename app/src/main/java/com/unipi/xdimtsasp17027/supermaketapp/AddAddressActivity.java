package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.prefs.Preferences;

public class AddAddressActivity extends AppCompatActivity {

    EditText roadEditText,numEditText,cityEditText;

    private FirebaseAuth mAuth;
    AlertDialog.Builder onBackPressedAlert;

    TextView textView1;
    SharedPreferences preferences;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);



        roadEditText=findViewById(R.id.roadEditText);
        numEditText=findViewById(R.id.numberEditText);
        cityEditText=findViewById(R.id.cityEditText);


        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView11);
            textView1.setText("Add delivery\n address");
            roadEditText.setHint("Road");
            numEditText.setHint("Number");
            cityEditText.setHint("City");
            button1=findViewById(R.id.button40530);
            button1.setText("Submit");

        }

        mAuth = FirebaseAuth.getInstance();
    }

    public void save(View view){
        if(!((roadEditText.getText().toString()).equals("") || (numEditText.getText().toString()).equals("")
                || (cityEditText.getText().toString()).equals(""))) {
            if(roadEditText.getText().toString().chars().allMatch(Character::isLetter) &&
                    cityEditText.getText().toString().chars().allMatch(Character::isLetter)){


                StringBuilder address=new StringBuilder();
                address.append(roadEditText.getText().toString()+",");
                address.append(numEditText.getText().toString()+",");
                address.append(cityEditText.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Customers").child(mAuth.getCurrentUser().getUid()).child("address").push().setValue(address.toString());




                startActivity(new Intent(getApplicationContext(),SelectAddressAcitvity.class));



            }else{
                if(preferences.getString("language","").equals("english")){

                    Toast.makeText(getApplicationContext(),"No acceptable character found",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Κάποιο πεδίο περιλαμβάνει μη αποδεκτό χαρακτήρα.",Toast.LENGTH_SHORT).show();
                }


            }


        }else{
            if(preferences.getString("language","").equals("english")){

                Toast.makeText(getApplicationContext(),"You have to fill out all fields",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(), "Θα πρέπει να συμπληρωθούν όλα τα πεδία.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {


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


        onBackPressedAlert = new AlertDialog.Builder(this);
        onBackPressedAlert.setMessage(message);
        onBackPressedAlert.setTitle(title);
        onBackPressedAlert.setCancelable(true);
        onBackPressedAlert.setNegativeButton(negative, null);
        onBackPressedAlert.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),SelectAddressAcitvity.class));

                    }
                });

        onBackPressedAlert.create().show();


    }
}