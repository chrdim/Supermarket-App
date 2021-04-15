package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectAddressAcitvity extends AppCompatActivity {


    ScrollView scrollView;
    Button addressButton;

    ImageView addImageView;
    TextView addTextView,textView1;

    TextView addressTextView;

    ArrayList<View> viewArrayList;

    ArrayList<String> addressArrayList;

    LinearLayout layout;

    String currentAddress;
    int counter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address_acitvity);

        counter=0;
        scrollView=findViewById(R.id.scrollView6);
        scrollView.setVisibility(View.INVISIBLE);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();


        addressArrayList=new ArrayList<String>();
        addImageView=findViewById(R.id.imageView505);
        addTextView=findViewById(R.id.textView17777);
        addImageView.setVisibility(View.INVISIBLE);
        addTextView.setVisibility(View.INVISIBLE);
        addressButton=findViewById(R.id.button189);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address");

        layout = findViewById(R.id.linearLayout6);
        viewArrayList = new ArrayList<View>();



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot datas : snapshot.getChildren()) {


                    View v = getLayoutInflater().inflate(R.layout.address_row, null, false);

                    addressTextView=(TextView)v.findViewById(R.id.textView9089);

                    currentAddress =datas.getValue(String.class);

                    addressArrayList.add(currentAddress);

                    addressTextView.setText(currentAddress);


                   viewArrayList.add(v);

                }


                if(viewArrayList.size()>0){
                    addressButton.setText(currentAddress);
                    for (int i = 0; i < viewArrayList.size(); i++) {
                        layout.addView(viewArrayList.get(i));
                        String address=addressArrayList.get(i);
                        viewArrayList.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addressButton.setText(address);
                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView1777);
            textView1.setText("Delivery address");

            addTextView.setText("Add an address");

            if(addressButton.getText().toString().equals("Διεύθυνση παράδοσης")){
                addressButton.setText("Delivery address");
            }

            button1=findViewById(R.id.buttonOrder);
            button1.setText("Order now");



        }

    }

    public void selectAddress(View view){

        counter++;
        if(counter%2==0){
            addressButton.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.arrowdown, 0);
            scrollView.setVisibility(View.INVISIBLE);

            addImageView.setVisibility(View.INVISIBLE);
            addTextView.setVisibility(View.INVISIBLE);

        }else{
            addressButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrowup, 0);
            scrollView.setVisibility(View.VISIBLE);

            addImageView.setVisibility(View.VISIBLE);
            addTextView.setVisibility(View.VISIBLE);
        }


    }

    public void goToNextActivity(View view){
        if(!((addressButton.getText().toString()).equals("Διεύθυνση παράδοσης")||(addressButton.getText().toString()).equals("Delivery address"))){
            editor.putString("customeraddress",addressButton.getText().toString());
            editor.apply();
            startActivity(new Intent(this,CustomerSupermarketSelectionActivity.class));
        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"Please insert an address",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Παρακαλώ  εισάγετε μία διεύθυνση", Toast.LENGTH_SHORT).show();
            }

        }

    }


    public void addAddress(View view){

        startActivity(new Intent(this,AddAddressActivity.class));
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
}