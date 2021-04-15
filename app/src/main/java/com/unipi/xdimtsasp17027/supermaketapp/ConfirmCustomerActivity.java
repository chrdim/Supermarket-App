package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmCustomerActivity extends AppCompatActivity {

    TextView emailTextView,userNameTextView,phoneTextView;
    SharedPreferences preferences;

    private FirebaseAuth mAuth;



    TextView textView1,textView2,textView3;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_customer);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        emailTextView=findViewById(R.id.textView21);
        userNameTextView=findViewById(R.id.textView23);


        phoneTextView=findViewById(R.id.textView22);


        mAuth = FirebaseAuth.getInstance();




        emailTextView.setText(preferences.getString("email",""));
        userNameTextView.setText(preferences.getString("username",""));
        phoneTextView.setText(preferences.getString("phone",""));

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView17);
            textView1.setText("Confirm Page");
            textView2=findViewById(R.id.textView19);
            textView2.setText("Username:");
            textView3=findViewById(R.id.textView20);
            textView3.setText("Phone");

            button1=findViewById(R.id.confirmButton45);
            button1.setText("Confirm");

        }



    }


    public void signUp(View view){
        mAuth.createUserWithEmailAndPassword(preferences.getString("email",""),
                preferences.getString("password",""))
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            setUserName();

                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });




    }

    public void setUserName(){

        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(preferences.getString("username","")+"--->"+"customer")

                .build();

        mAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                FirebaseDatabase.getInstance().getReference().child("Customers").child(mAuth.getCurrentUser().getUid()).child("phone").setValue(phoneTextView.getText().toString());
                signIn();
            }
        });

        

    }

    public void signIn(){
        mAuth.signInWithEmailAndPassword(preferences.getString("email",""),
                preferences.getString("password","")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //τον μεταφέρουμε στο επόμενο Activity


                startActivity(new Intent(getApplicationContext(),SelectAddressAcitvity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {


        finish();
        startActivity(new Intent(getApplicationContext(),RegisterPassword.class));




    }
}