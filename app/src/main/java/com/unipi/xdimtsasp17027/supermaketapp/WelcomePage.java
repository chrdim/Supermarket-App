package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomePage extends AppCompatActivity {


    TextView textViewSignUp;
    EditText emailAddressEditText,passwordEditText;

    private FirebaseAuth mAuth;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    TextView textView1,textView2,textView3,textView4;
    EditText editText1;
    Button button1;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_welcome_page);



            preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if(preferences.getString("language","").equals("english")){
                textView1=findViewById(R.id.textView2);
                textView1.setText("Welcome,");
                textView2=findViewById(R.id.textView4);
                textView2.setText("login to continue");
                textView3=findViewById(R.id.textView1);
                textView3.setText("Don't have an account?");
                textView4=findViewById(R.id.textView3);
                textView4.setText("Register");
                editText1=findViewById(R.id.passEditText1);
                editText1.setHint("Password");
                button1=findViewById(R.id.loginΒutton);
                button1.setText("Login");
            }


            textViewSignUp = findViewById(R.id.textView3);


            emailAddressEditText=findViewById(R.id.emailAddressEditText1);
            passwordEditText=findViewById(R.id.passEditText1);

            mAuth = FirebaseAuth.getInstance();


            editor=preferences.edit();

        }


        public void goToRegisterActivity(View view) {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        }

        public void login(View view){
            //έλεγχος για το αν είναι συμπληρωμένα όλα τα πεδία
            if(!(emailAddressEditText.getText().toString().equals("") || (passwordEditText.getText().toString()).equals(""))){

                //σύνδεση χρήστη με email και password
                mAuth.signInWithEmailAndPassword(emailAddressEditText.getText().toString(),passwordEditText.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {


                                    String[] words = mAuth.getCurrentUser().getDisplayName().split("--->");
                                    if(words[1].equals("admin")){
                                        startActivity(new Intent(getApplicationContext(),AdminMainPage.class));
                                    }else if(words[1].equals("customer")){

                                        editor.putString("customerName",words[0]);
                                        editor.apply();


                                        startActivity(new Intent(getApplicationContext(),SelectAddressAcitvity.class));
                                   }





                                }
                                //έλεγχος για το αν το email είναι σε σωστή μορφή
                                else if(!(Patterns.EMAIL_ADDRESS.matcher(emailAddressEditText.getText().toString()).matches())){
                                    Toast.makeText(getApplicationContext(),"Η μορφή του email δεν είναι έγκυρη",Toast.LENGTH_SHORT).show();
                                }
                                //έλεγχος για το αν υπάρχει χρήστης με τα στοιχεία που εισήχθησαν
                                else{
                                    Toast.makeText(getApplicationContext(),"Τα παραπάνω στοιχεία δεν αντιστοιχούν σε κάποιον χρήστη.",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(),"Θα πρέπει να συμπληρωθούν όλα τα πεδία.",Toast.LENGTH_SHORT).show();

            }

        }



    public void onBackPressed() {


        finish();
        startActivity(new Intent(getApplicationContext(),ChooseLanguageActivity.class));




    }
}
