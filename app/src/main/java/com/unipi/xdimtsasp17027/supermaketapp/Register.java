package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    Button customerButton,adminButton;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        customerButton=findViewById(R.id.customerButton);
        adminButton=findViewById(R.id.adminButton);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();


        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView5);
            textView1.setText("Firstly");
            textView2=findViewById(R.id.textView6);
            textView2.setText("Select the kind of account you want to create");
            customerButton.setText("Customer");
            adminButton.setText("Supermarket\nAdmin");

        }
    }

    public void setUserAsCustomer(View view){
        editor.putString("userProperty","customer");
        editor.apply();
        startActivity(new Intent(this, RegisterCustomerEmailNamePhone.class));
    }

    public void setUserAsAdmin(View view){
        editor.putString("userProperty","admin");
        editor.apply();
        startActivity(new Intent(this,RegisterAdminEmailName.class));
    }

    @Override
    public void onBackPressed() {

        finish();
        startActivity(new Intent(getApplicationContext(),WelcomePage.class));

    }


}