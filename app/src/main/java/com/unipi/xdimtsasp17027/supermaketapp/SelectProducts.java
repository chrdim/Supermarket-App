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
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectProducts extends AppCompatActivity {

    Spinner subCategories;
    ArrayList<ImageView> deleteImageViews,addImageViews;

    ArrayList<Integer> quantitiesFirebase;

    ArrayList<String>productsNames,prices;

    ArrayList<TextView>productQuantityTextViews;

    ArrayList<View> viewArrayList;

    LinearLayout layout;

    TextView productNameTextView,priceTextView,quantityProduct,textView1,textView2;
    ImageView productImageView,addImageView,deleteImageView;

    double finalCost;
    SharedPreferences preferences;

    Button bucketButton,button1;

    SQLiteDatabase db;



    String items[];
    String items2[];

    ArrayList<String>productNamesFromSql;
    ArrayList<String>productQuantitiesFromSql;

    String subCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_products);


        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        db = openOrCreateDatabase("shop2", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS shop2(productName TEXT,productPrice TEXT,productQuantity TEXT,category TEXT,subCategory TEXT,storeIdofProduct TEXT)");


        productNamesFromSql=new ArrayList<String>();
        productQuantitiesFromSql=new ArrayList<String>();

        bucketButton=findViewById(R.id.button2344);
        finalCost=0.0;
        Cursor cursor = db.rawQuery("SELECT * FROM shop2",null);
        if (cursor.getCount()>0) {

            while (cursor.moveToNext()){
                finalCost+=Double.parseDouble(cursor.getString(1));

            }
            if (preferences.getString("language", "").equals("english")) {
                bucketButton.setText("BUCKET " + (String.format("%.2f", finalCost)) + " €");
            } else {
                bucketButton.setText("ΚΑΛΑΘΙ " + (String.format("%.2f", finalCost)) + " €");
            }
            bucketButton.setEnabled(true);


        }else{
            bucketButton.setEnabled(false);
            if (preferences.getString("language", "").equals("english")) {
                bucketButton.setText("BUCKET " + (String.format("%.2f", finalCost)) + " €");
            } else {
                bucketButton.setText("ΚΑΛΑΘΙ " + (String.format("%.2f", finalCost)) + " €");
            }
        }



        subCategories=findViewById(R.id.spinner42);











        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView5001123);
            textView1.setText("Select Products");
            textView2=findViewById(R.id.textView507112);
            textView2.setText("Select subcategory");
            button1=findViewById(R.id.button2343);
            button1.setText("Search");





        }




        if(getIntent().getStringExtra("category").equals("Φρούτα")){
            if(preferences.getString("language","").equals("english")){

                items= new String[]{"Dried fruits", "Fresh fruits"};
                items2=new String[]{"Αποξηραμένα φρόυτα", "Φρέσκα φρούτα"};
            }else{
                items= new String[]{"Αποξηραμένα φρόυτα", "Φρέσκα φρούτα"};
            }


        }else if(getIntent().getStringExtra("category").equals("Λαχανικά")){

            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Fresh vegetables", "Frozen vegetables","Prepared salads"};
                items2=new String[]{"Φρέσκα λαχανικά", "Κατεψυγμένα λαχανικά","Έτοιμες Σαλάτες"};
            }else{
                items= new String[]{"Φρέσκα λαχανικά", "Κατεψυγμένα λαχανικά","Έτοιμες Σαλάτες"};
            }
        }else if(getIntent().getStringExtra("category").equals("Γαλακτοκομικά")){

            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Milk", "Cheeses","Yogurts"};
                items2=new String[]{"Γάλατα", "Τυροκομικά","Γιαούρτια"};
            }else{
                items=new String[]{"Γάλατα", "Τυροκομικά","Γιαούρτια"};
            }
        }else if(getIntent().getStringExtra("category").equals("Κάβα")){
            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Wines", "Drinks","Beers"};
                items2=new String[]{"Κρασιά", "Ποτά","Μπύρες"};
            }else{
                items=new String[]{"Κρασιά", "Ποτά","Μπύρες"};
            }

        }else if(getIntent().getStringExtra("category").equals("Απορρυπαντικά και είδη καθαρισμού")){
            if(preferences.getString("language","").equals("english")){
                items=new String[]{"General Purspose Cleaners", "Detergents"};
                items2=new String[]{"Καθαριστικά Γενικής Χρήσης", "Απορρυπαντικά"};
            }else{
                items=new String[]{"Καθαριστικά Γενικής Χρήσης", "Απορρυπαντικά"};
            }

        }else if(getIntent().getStringExtra("category").equals("Τροφές και έιδη κατοικιδίων")){
            if(preferences.getString("language","").equals("english")){
                items=  new String[]{"For cats", "For dogs","For fish,birds etc"};
                items2=new String[]{"Τροφές και είδη για γάτες", "Τροφές και είδη για σκύλους","Τροφές και είδη για ψάρια και πτηνά"};
            }else{
                items=new String[]{"Τροφές και είδη για γάτες", "Τροφές και είδη για σκύλους","Τροφές και είδη για ψάρια και πτηνά"};
            }

        }else if(getIntent().getStringExtra("category").equals("Τρόφιμα παντοπωλείου")){
            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Spices", "Canned food","Legumes","Eggs","Oils","Rices"};
                items2=new String[]{"Μπαχαρικά", "Κονσέρβες","Όσπρια","Αυγά","Λάδια","Ρύζια"};
            }else{
                items=new String[]{"Μπαχαρικά", "Κονσέρβες","Όσπρια","Αυγά","Λάδια","Ρύζια"};
            }

        }else if(getIntent().getStringExtra("category").equals("Είδη προσωπικής υγιεινής")){
            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Pharmacy", "Βody care","Hair care","Οral hygiene","Shaving"};
                items2=new String[]{"Είδη φαρμακείου", "Περιποίηση σώματος","Περιποίηση μαλλιών","Στοματική υγιεινή","Είδη ξυρίσματος"};
            }else{
                items=new String[]{"Είδη φαρμακείου", "Περιποίηση σώματος","Περιποίηση μαλλιών","Στοματική υγιεινή","Είδη ξυρίσματος"};
            }

        }else if(getIntent().getStringExtra("category").equals("Ξηροί καρποί και σνακ")){
            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Crackers", "Chips","Hair care","Nuts"};
                items2=new String[]{"Κράκερς", "Πατατάκια,γαριδάκια","Ξηροί καρποί"};
            }else{
                items=new String[]{"Κράκερς", "Πατατάκια,γαριδάκια","Ξηροί καρποί"};
            }

        }else if(getIntent().getStringExtra("category").equals("Αναψυκτικά και Νερά")){

            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Juices", "Soft and energy drinks","Water"};
                items2=new String[]{"Χυμοί", "Αναψυκτικά,σόδες και ενεργειακά ποτά","Νερά"};
            }else{
                items=new String[]{"Χυμοί", "Αναψυκτικά,σόδες και ενεργειακά ποτά","Νερά"};
            }
        }else if(getIntent().getStringExtra("category").equals("Είδη Αρτοζαχαροπλαστείου")){

            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Sweets", "Bread","Tortillas"};
                items2=new String[]{"Γλυκά", "Ψωμί και αρτοπαρασκευάσματα","Πίτες και τορτίγιες"};
            }else{
                items= new String[]{"Γλυκά", "Ψωμί και αρτοπαρασκευάσματα","Πίτες και τορτίγιες"};
            }

        }else if(getIntent().getStringExtra("category").equals("Κρεατικά")){

            if(preferences.getString("language","").equals("english")){
                items= new String[]{"Fresh meat", "Frozen meat","Cold cuts"};
                items2=new String[]{"Φρέσκα κρέατα", "Κατεψυγμένα κρέατα","Αλλαντικά"};
            }else{
                items= new String[]{"Φρέσκα κρέατα", "Κατεψυγμένα κρέατα","Αλλαντικά"};
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        subCategories.setAdapter(adapter);


    }

    public void search(View view){


        deleteImageViews=new ArrayList<ImageView>();
        addImageViews=new ArrayList<ImageView>();

        viewArrayList = new ArrayList<View>();
        productQuantityTextViews=new ArrayList<TextView>();

        prices=new ArrayList<String>();
        quantitiesFirebase =new ArrayList<Integer>();
        productsNames=new ArrayList<String>();
        subCategory=subCategories.getSelectedItem().toString();
        if(preferences.getString("language","").equals("english")){
            int i= Arrays.asList(items).indexOf(subCategories.getSelectedItem().toString());
            subCategory=items2[i];


        }





        layout = findViewById(R.id.linearLayout22234);
        layout.removeAllViewsInLayout();


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Supermakets").child(preferences.getString("storeid","")).child("products").child(getIntent().getStringExtra("category")).child(subCategory);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot datas : snapshot.getChildren()) {

                    View v = getLayoutInflater().inflate(R.layout.product_row_customer, null, false);

                    productNameTextView = (TextView) v.findViewById(R.id.textView80042);
                    productNameTextView.setText(datas.getKey());



                    productsNames.add(datas.getKey());

                    deleteImageView = (ImageView) v.findViewById(R.id.imageView20343244);
                    addImageView = (ImageView) v.findViewById(R.id.imageView2034444);

                    deleteImageViews.add(deleteImageView);
                    addImageViews.add(addImageView);

                    productImageView=(ImageView) v.findViewById(R.id.imageView522133);

                    String url=datas.child("Image").getValue().toString();
                    Picasso.get().load(Uri.parse(url)).into(productImageView);


                    quantityProduct=(TextView)v.findViewById(R.id.textView800432345);
                    productQuantityTextViews.add(quantityProduct);

                    priceTextView=(TextView)v.findViewById(R.id.textView800432);
                    priceTextView.setText(datas.child("Price").getValue().toString()+"€");
                    prices.add(datas.child("Price").getValue().toString());

                    quantitiesFirebase.add(Integer.parseInt(datas.child("Quantity").getValue().toString()));


                    viewArrayList.add(v);
                }


                for (int i = 0; i < viewArrayList.size(); i++) {


                    if(viewArrayList.get(i).getParent() != null) {
                        ((ViewGroup)viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                    }

                    Cursor cursor = db.rawQuery("SELECT * FROM shop2",null);
                    if (cursor.getCount()>0) {
                        while (cursor.moveToNext()) {
                            if(cursor.getString(0).equals(productsNames.get(i))){
                                productQuantityTextViews.get(i).setText(cursor.getString(2));
                                productNamesFromSql.add(cursor.getString(0));

                            }

                        }
                    }





                    layout.addView(viewArrayList.get(i));


                    TextView productQuantityTextView=productQuantityTextViews.get(i);


                    final int index=i;
                    deleteImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (bucketButton.isEnabled() == true) {
                                if (!(productQuantityTextView.getText().toString().equals("0"))) {
                                    int quantity = Integer.parseInt(productQuantityTextView.getText().toString());
                                    quantity--;
                                    productQuantityTextView.setText(String.valueOf(quantity));
                                    if (quantity > 0) {
                                        db.execSQL("UPDATE shop2 SET productQuantity=? WHERE productName=?", new String[]{String.valueOf(quantity), productsNames.get(index)});
                                    } else {
                                        db.execSQL("DELETE FROM shop2 WHERE productName=?", new String[]{productsNames.get(index)});
                                        productNamesFromSql.remove(productsNames.get(index));
                                    }

                                    finalCost -= Double.parseDouble(prices.get(index));
                                    if (String.format("%.2f", finalCost).equals("-0.00")) {
                                        finalCost = 0.0;
                                        bucketButton.setEnabled(false);
                                    }
                                    if (finalCost == 0.0) {
                                        bucketButton.setEnabled(false);
                                    }
                                    if (preferences.getString("language", "").equals("english")) {
                                        bucketButton.setText("BUCKET " + (String.format("%.2f", finalCost)) + " €");
                                    } else {
                                        bucketButton.setText("ΚΑΛΑΘΙ " + (String.format("%.2f", finalCost)) + " €");
                                    }

                                }
                            }
                        }
                    });

                    addImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            bucketButton.setEnabled(true);

                            int quantity = Integer.parseInt(productQuantityTextView.getText().toString());

                            if (!(productNamesFromSql.contains(productsNames.get(index)))) {
                                if (quantity + 1 <= quantitiesFirebase.get(index)) {
                                    quantity++;
                                    db.execSQL("INSERT INTO shop2 VALUES('" + productsNames.get(index) + "','" + prices.get(index) + "','" + String.valueOf(quantity) + "','" + getIntent().getStringExtra("category") + "','" + subCategory + "','" + preferences.getString("storeid", "") + "')");

                                    productNamesFromSql.add(productsNames.get(index));

                                    productQuantityTextView.setText(String.valueOf(quantity));
                                    finalCost+=Double.parseDouble(prices.get(index));
                                    if(preferences.getString("language","").equals("english")){
                                        bucketButton.setText("BUCKET "+ (String.format("%.2f", finalCost)) +" €");
                                    }else{
                                        bucketButton.setText("ΚΑΛΑΘΙ "+ (String.format("%.2f", finalCost)) +" €");
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "οχι αλλη ποσοτητα", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                if (quantity + 1 <= quantitiesFirebase.get(index)) {
                                    quantity++;


                                    db.execSQL("UPDATE shop2 SET productQuantity=? WHERE productName=?", new String[]{String.valueOf(quantity), productsNames.get(index)});

                                    productQuantityTextView.setText(String.valueOf(quantity));
                                    finalCost+=Double.parseDouble(prices.get(index));
                                    if(preferences.getString("language","").equals("english")){
                                        bucketButton.setText("BUCKET "+ (String.format("%.2f", finalCost)) +" €");
                                    }else{
                                        bucketButton.setText("ΚΑΛΑΘΙ "+ (String.format("%.2f", finalCost)) +" €");
                                    }

                                } else {
                                    if(preferences.getString("language","").equals("english")){
                                        Toast.makeText(getApplicationContext(), "There is no extra available", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Δεν υπάρχει επιπλέον διαθέσιμη ποσότητα", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }


                        }

                    });

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
        startActivity(new Intent(getApplicationContext(),ActivityProductsCategories.class));


    }
    public  void confirmOrder(View view){
        startActivity(new Intent(getApplicationContext(),CustomerConfirmOrder.class).putExtra("finalcost",finalCost));
    }


}