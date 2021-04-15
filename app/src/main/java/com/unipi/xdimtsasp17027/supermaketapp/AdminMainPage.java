package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class AdminMainPage extends AppCompatActivity {
    ImageView logoImageView;
    TextView storeNameTextView,textView1,textView2,textView3,textView4;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        logoImageView=findViewById(R.id.imageView1000);
        storeNameTextView=findViewById(R.id.textView1002);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String[] words = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().split("--->");
        storeNameTextView.setText(words[0]);



        FirebaseDatabase.getInstance().getReference("Supermakets").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String logoUrl=snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("logo").getValue().toString();
                Picasso.get().load(Uri.parse(logoUrl)).into(logoImageView);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView1008);
            textView1.setText("Display products");
            textView2=findViewById(R.id.textView1003);
            textView2.setText("Add a product");
            textView3=findViewById(R.id.textView1004);
            textView3.setText("Display orders");
            textView4=findViewById(R.id.textView1006);
            textView4.setText("Logout");

        }




    }

    @Override
    public void onBackPressed() {

        String message;
        String title;
        String positive;
        String negative;
        if(preferences.getString("language","").equals("english")){
            message="Exit app?";
            title="Caution";
            negative="Cancel";
            positive="Exit";
        }else {
            message="Έξοδος απο την εφαρμογή";
            title="ΠΡΟΣΟΧΗ";
            negative="Άκυρο";
            positive="Έξοδος";
        }
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(negative, null)
                .setPositiveButton(positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();


                            }
                        }).create().show();


    }

    public void insertProducts(View view){

        startActivity(new Intent(getApplicationContext(),InsertProducts.class));

    }

    public void showProducts(View view){
        startActivity(new Intent(getApplicationContext(),ShowProducts.class));
    }

    public void showOrders(View view){
        startActivity(new Intent(getApplicationContext(),AdminOrdersActivity.class));
    }

    public void logout(View view){
        finishAffinity();
    }

}