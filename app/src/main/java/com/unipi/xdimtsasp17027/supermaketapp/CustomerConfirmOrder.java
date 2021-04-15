package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CustomerConfirmOrder extends AppCompatActivity {


    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SQLiteDatabase db;
    ArrayList<View> viewArrayList;
    LinearLayout layout;
    ArrayList<ImageView> deleteImageViews,addImageViews;
    ArrayList<TextView>productQuantityTextViews;
    ArrayList<String>productsNamesList,productsQuantityList,productsPriceList,productCategoriesList,productsSubCategoriesList;
    TextView phoneTextView,nameTextView,addressTextView,productNameTextView,productQuantityTextView,priceTextView;


    ImageView deleteImageView,addImageView;


    ArrayList<Integer> quantitiesFirebase;

    Button sendButton;
    Double finalCost;
    String storeid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_confirm_order);

        productsNamesList=new ArrayList<String>();
        productsQuantityList=new ArrayList<String>();
        productsPriceList=new ArrayList<String>();
        productCategoriesList=new ArrayList<String>();
        productsSubCategoriesList=new ArrayList<String>();


        finalCost=getIntent().getDoubleExtra("finalcost",0.0);
        db = openOrCreateDatabase("shop2", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS shop2(productName TEXT,productPrice TEXT,productQuantity TEXT,category TEXT,subCategory TEXT,storeIdofProduct TEXT)");

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();

        phoneTextView=findViewById(R.id.textView50011234);
        nameTextView=findViewById(R.id.textView50011235);
        addressTextView=findViewById(R.id.textView50011236);
        sendButton=findViewById(R.id.button23449);


        nameTextView.setText(preferences.getString("customerName",""));
        addressTextView.setText(preferences.getString("customeraddress",""));

        deleteImageViews=new ArrayList<ImageView>();
        addImageViews=new ArrayList<ImageView>();
        productQuantityTextViews=new ArrayList<TextView>();

        quantitiesFirebase =new ArrayList<Integer>();


        if(preferences.getString("language","").equals("english")){
            sendButton.setText("SEND ORDER "+ (String.format("%.2f", finalCost)) +" €");
        }else{
            sendButton.setText("ΑΠΟΣΤΟΛΗ "+ (String.format("%.2f", finalCost)) +" €");
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {
                    String phone = snapshot.child("phone").getValue().toString();
                    phoneTextView.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        viewArrayList = new ArrayList<View>();
        layout = findViewById(R.id.linearLayout222349);
        layout.removeAllViewsInLayout();
        viewArrayList.clear();

        Cursor cursor = db.rawQuery("SELECT * FROM shop2",null);
        if (cursor.getCount()>0){

            while (cursor.moveToNext()){
                productsNamesList.add(cursor.getString(0));
                productsPriceList.add(cursor.getString(1));
                productsQuantityList.add(cursor.getString(2));
                productCategoriesList.add(cursor.getString(3));
                productsSubCategoriesList.add(cursor.getString(4));

                 storeid = cursor.getString(5);

                DatabaseReference myRef2 = database.getReference("Supermakets").child(storeid).child("products").child(cursor.getString(3)).child(cursor.getString(4)).child(cursor.getString(0));
                myRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        quantitiesFirebase.add(Integer.parseInt(snapshot.child("Quantity").getValue().toString()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                View v = getLayoutInflater().inflate(R.layout.confirm_products, null, false);


                deleteImageView = (ImageView) v.findViewById(R.id.imageView2034324499);
                addImageView = (ImageView) v.findViewById(R.id.imageView203444499);
                deleteImageViews.add(deleteImageView);
                addImageViews.add(addImageView);



                productNameTextView = (TextView) v.findViewById(R.id.textView8004299);
                productNameTextView.setText(cursor.getString(0));
                priceTextView=(TextView)v.findViewById(R.id.textView80043299);
                priceTextView.setText(cursor.getString(1) +" €");
                productQuantityTextView=(TextView)v.findViewById(R.id.textView80043234599);
                productQuantityTextView.setText(cursor.getString(2));

                productQuantityTextViews.add(productQuantityTextView);

                viewArrayList.add(v);


            }






            for (int i = 0; i < viewArrayList.size(); i++) {

                if(viewArrayList.get(i).getParent() != null) {
                    ((ViewGroup)viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                }

                layout.addView(viewArrayList.get(i));

                final int index=i;

                deleteImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!(sendButton.getText().toString().equals("AΠΟΣΤΟΛΗ 0.0 €"))||sendButton.getText().toString().equals("SEND ORDER 0.0 €")){

                            if(!(productsQuantityList.get(index).equals("0"))){
                                finalCost-=Double.parseDouble(productsPriceList.get(index));
                                int quantity = Integer.parseInt(productQuantityTextViews.get(index).getText().toString());
                                quantity--;

                                if(String.format("%.2f", finalCost).equals("-0.00")){
                                    finalCost=0.0;
                                    sendButton.setEnabled(false);
                                }
                                if(finalCost==0.0){
                                    sendButton.setEnabled(false);
                                }

                                productsQuantityList.set( index, String.valueOf(quantity) );
                                productQuantityTextViews.get(index).setText(String.valueOf(quantity));

                                if(preferences.getString("language","").equals("english")){
                                    sendButton.setText("SEND ORDER "+ (String.format("%.2f", finalCost)) +" €");
                                }else{
                                    sendButton.setText("ΑΠΟΣΤΟΛΗ "+ (String.format("%.2f", finalCost)) +" €");
                                }



                            }
                        }

                    }



                });

                addImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int quantity = Integer.parseInt(productsQuantityList.get(index));
                        if(quantity+1<=quantitiesFirebase.get(index)){
                            finalCost+=Double.parseDouble(productsPriceList.get(index));



                            quantity++;

                            productsQuantityList.set( index, String.valueOf(quantity) );
                            productQuantityTextViews.get(index).setText(String.valueOf(quantity));


                            sendButton.setEnabled(true);

                            if(preferences.getString("language","").equals("english")){
                                sendButton.setText("SEND ORDER "+ (String.format("%.2f", finalCost)) +" €");
                            }else{
                                sendButton.setText("ΑΠΟΣΤΟΛΗ "+ (String.format("%.2f", finalCost)) +" €");
                            }

                        }else{
                            if(preferences.getString("language","").equals("english")){
                                Toast.makeText(getApplicationContext(),"There is no additional quantity of the product available ",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Δεν υπάρχει επιπλέον διαθέσιμη ποσότητα του προιόντος",Toast.LENGTH_SHORT).show();
                            }

                        }

                    }

                });


            }

        }

    }



    public void sendOrder(View view) {
        double minimumCost=Double.parseDouble(preferences.getString("minimumCost",""));
        if(minimumCost<=finalCost){



            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"Your order has been sent",Toast.LENGTH_SHORT).show();;
            }else{
                Toast.makeText(getApplicationContext(),"Η παραγγελία σας εστάλη",Toast.LENGTH_SHORT).show();
            }

            Cursor cursor2 = db.rawQuery("DELETE  FROM shop2", null);
            if (cursor2.getCount() >= 0) {
                StringBuilder builder2 = new StringBuilder();
                while (cursor2.moveToNext()) {

                    builder2.append("-----------------------------------\n");
                }

            }

            for(int i = 0; i < quantitiesFirebase.size(); i++) {
                if(productsQuantityList.get(i).equals("0")){
                    productsQuantityList.remove(i);
                    productsNamesList.remove(i);
                    productsSubCategoriesList.remove(i);
                    productsPriceList.remove(i);
                    productCategoriesList.remove(i);
                    quantitiesFirebase.remove(i);
                }
            }


            for(int i = 0; i < quantitiesFirebase.size(); i++) {
                if (viewArrayList.get(i).getParent() != null) {
                    ((ViewGroup) viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                }
                int newFirebaseQuantity=quantitiesFirebase.get(i)-Integer.parseInt(productsQuantityList.get(i));
                FirebaseDatabase.getInstance().getReference().child("Supermakets").child(storeid).child("products").child(productCategoriesList.get(i))
                        .child(productsSubCategoriesList.get(i)).child(productsNamesList.get(i)).child("Quantity").setValue(newFirebaseQuantity)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
            }

            DatabaseReference orderRef=FirebaseDatabase.getInstance().getReference().child("Supermakets").child(storeid).child("Orders").push();

            orderRef
                    .child("CustomerInfo").child("Address").setValue(addressTextView.getText().toString());

            orderRef
                    .child("CustomerInfo").child("Name").setValue(nameTextView.getText().toString());

            orderRef
                    .child("CustomerInfo").child("Phone").setValue(phoneTextView.getText().toString());


            String products="";
            for(int i = 0; i < quantitiesFirebase.size(); i++) {
                products+=productsNamesList.get(i) +" "+ productsQuantityList.get(i)+",";

            }
            orderRef
                    .child("Products").setValue(products);

            orderRef
                    .child("TotalPrice").setValue(String.format("%.2f", finalCost));


            startActivity(new Intent(getApplicationContext(),ActivityProductsCategories.class));

        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"You have not completed the minimum order cost",Toast.LENGTH_SHORT).show();;
            }else{
                Toast.makeText(getApplicationContext(),"Δεν έχετε συμπληρώσει το ελάχιστο κόστος παραγγελίας",Toast.LENGTH_SHORT).show();
            }
        }


    }

    @Override
    public void onBackPressed() {

        finish();
        startActivity(new Intent(getApplicationContext(),ActivityProductsCategories.class));

    }

}