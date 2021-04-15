package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrdersActivity extends AppCompatActivity {

    LinearLayout layout;
    ArrayList<View> viewArrayList;
    ArrayList<TextView> textViews,NameTextViews,addressTextViews,phoneTextViews,priceTextViews,productsTextViews,ProintaViews;
    TextView textView,NameTextView,addressTextView,phoneTextView,priceTextView,productsTextView,ProintaView,textView1;
    ArrayList<ImageView> nameImageViews,addressImageViews,phoneImageViews;
    ImageView nameImageView,addressImageView,phoneImageView;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);


        nameImageViews=new ArrayList<ImageView>();
        addressImageViews=new ArrayList<ImageView>();
        phoneImageViews=new ArrayList<ImageView>();

        textViews=new ArrayList<TextView>();
        NameTextViews=new ArrayList<TextView>();
        addressTextViews=new ArrayList<TextView>();
        phoneTextViews=new ArrayList<TextView>();
        priceTextViews=new ArrayList<TextView>();
        productsTextViews=new ArrayList<TextView>();
        ProintaViews=new ArrayList<TextView>();

        viewArrayList = new ArrayList<View>();
        layout = findViewById(R.id.linearLayout2223498);
        layout.removeAllViewsInLayout();
        viewArrayList.clear();
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        textView1=findViewById(R.id.textView1002);
        if(preferences.getString("language","").equals("english")) {
            textView1.setText("Orders");
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Orders");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {

                    View v = getLayoutInflater().inflate(R.layout.admin_order_row, null, false);
                    // String name=datas.getKey();
                    NameTextView = (TextView) v.findViewById(R.id.textView122334);
                    textView = (TextView) v.findViewById(R.id.textView);
                    addressTextView = (TextView) v.findViewById(R.id.textView123243);
                    phoneTextView= (TextView) v.findViewById(R.id.textView124365);
                    priceTextView = (TextView) v.findViewById(R.id.textView45353245323);
                    productsTextView= (TextView) v.findViewById(R.id.textView453532453);
                    ProintaView= (TextView) v.findViewById(R.id.textView45353245);


                    NameTextView.setText(datas.child("CustomerInfo").child("Name").getValue().toString());
                    addressTextView.setText(datas.child("CustomerInfo").child("Address").getValue().toString());
                    phoneTextView.setText(datas.child("CustomerInfo").child("Phone").getValue().toString());
                    if(preferences.getString("language","").equals("english")){
                        priceTextView.setText("Order cost:"+datas.child("TotalPrice").getValue().toString()+"€" );
                    }else{
                        priceTextView.setText("Κόστος Παραγγελίας:"+datas.child("TotalPrice").getValue().toString()+"€" );
                    }

                    if(preferences.getString("language","").equals("english")){
                        ProintaView.setText("Products");
                    }

                    if(preferences.getString("language","").equals("english")){
                        textView.setText("Customer Informations" );
                    }


                    String[] products = datas.child("Products").getValue().toString().split(",");
                    for(String product:products){
                        productsTextView.setText(productsTextView.getText()+"\n"+product);
                    }


                    nameImageView = (ImageView) v.findViewById(R.id.imageView32453);
                    addressImageView = (ImageView) v.findViewById(R.id.imageView6565654);
                    phoneImageView = (ImageView) v.findViewById(R.id.imageView5464367567);

                    nameImageViews.add(nameImageView);
                    addressImageViews.add(addressImageView);
                    phoneImageViews.add(phoneImageView);

                    NameTextViews.add(NameTextView);
                    textViews.add(textView);
                    addressTextViews.add(addressTextView);
                    phoneTextViews.add(phoneTextView);
                    priceTextViews.add(priceTextView);
                    productsTextViews.add(productsTextView);
                    ProintaViews.add(ProintaView);

                    viewArrayList.add(v);

                }
                for (int i = 0; i < viewArrayList.size(); i++) {


                    if (viewArrayList.get(i).getParent() != null) {
                        ((ViewGroup) viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                    }

                    layout.addView(viewArrayList.get(i));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onBackPressed() {


        finish();
        startActivity(new Intent(getApplicationContext(),AdminMainPage.class));


    }
}
