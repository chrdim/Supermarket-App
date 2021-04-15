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
import android.text.method.BaseKeyListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ActivityProductsCategories extends AppCompatActivity {

    TextView timeTextView,priceTextView,storeNameTextView;
    SharedPreferences preferences;
    ImageView storeImageView,storeLogoImageView;
    SharedPreferences.Editor editor;
    TextView textView1,textView2,textView3;
    TextView fruitTextView,meatTextView,bakeryTextView,vegetableTextView,milkTextView,cleaningTextView,petTextView,marketTextView;
    TextView personalCleaningTextView,snackTextView,drinksTextView,alcoholTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_categories);


        timeTextView=findViewById(R.id.textView14053414);
        priceTextView=findViewById(R.id.textView1405341);
        storeNameTextView=findViewById(R.id.textView14050);
        storeImageView=findViewById(R.id.imageView2332);
        storeLogoImageView=findViewById(R.id.imageView179593);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();

        FirebaseDatabase.getInstance().getReference("Supermakets").child(preferences.getString("storeid","")).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                storeNameTextView.setText(snapshot.child("name").getValue().toString());
                priceTextView.setText(snapshot.child("minimum order").getValue().toString()+"€");
                timeTextView.setText(snapshot.child("time").getValue().toString());

                editor.putString("minimumCost",snapshot.child("minimum order").getValue().toString());
                editor.apply();
                String logoUrl=snapshot.child("logo").getValue().toString();
                Picasso.get().load(Uri.parse(logoUrl)).into(storeLogoImageView);

                String imageUrl=snapshot.child("image").getValue().toString();
                Picasso.get().load(Uri.parse(imageUrl)).into(storeImageView);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView1405341445);
            textView1.setText("hours");
            textView2=findViewById(R.id.textView14053);
            textView2.setText("Time");
            textView3=findViewById(R.id.textView140534);
            textView3.setText("Min Order");

            fruitTextView=findViewById(R.id.textView358834080);
            fruitTextView.setText("FRUITS");
            meatTextView=findViewById(R.id.textView351180);
            meatTextView.setText("MEAT");
            bakeryTextView=findViewById(R.id.textView221180);
            bakeryTextView.setText("BAKERY");
            vegetableTextView=findViewById(R.id.textView223371180);
            vegetableTextView.setText("VEGETABLES");
            milkTextView=findViewById(R.id.textView34354);
            milkTextView.setText("DAIRY PRODUCTS");
            cleaningTextView=findViewById(R.id.textView3435445);
            cleaningTextView.setText("DETERGENTS");
            petTextView=findViewById(R.id.textView343544455);
            petTextView.setText("PET ITEMS");
            marketTextView=findViewById(R.id.textView34354334455);
            marketTextView.setText("GROCERY");

            personalCleaningTextView=findViewById(R.id.textView33354334455);
            personalCleaningTextView.setText("PERSONAL HYGIENE");
            snackTextView=findViewById(R.id.textView3674334455);
            snackTextView.setText("SNACKS");
            drinksTextView=findViewById(R.id.textView367778);
            drinksTextView.setText("WATER AND\n SOFT DRINKS");
            alcoholTextView=findViewById(R.id.textView3634457778);
            alcoholTextView.setText("ALCOHOL");
        }

    }

    @Override
    public void onBackPressed() {

        String message;
        String title;
        String positive;
        String negative;
        if(preferences.getString("language","").equals("english")){
            message="If you didn't send the order,it will be lost";
            title="Caution";
            negative="Cancel";
            positive="Back";
        }else {
            message="Αν δεν έχετε στείλει την παραγγελία σας θα χαθεί";
            title="ΠΡΟΣΟΧΗ";
            negative="Άκυρο";
            positive="Πίσω";
        }
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setNegativeButton(negative, null)
                .setPositiveButton(positive,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                finish();
                                startActivity(new Intent(getApplicationContext(),CustomerSupermarketSelectionActivity.class));

                            }
                        }).create().show();




    }

    public void fruit(View view){

        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Φρούτα"));
    }
    public void vegetables(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Λαχανικά"));
    }
    public void milk(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Γαλακτοκομικά"));
    }
    public void drinks(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Κάβα"));
    }
    public void cleaners(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Απορρυπαντικά και είδη καθαρισμού"));
    }
    public void pets(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Τροφές και έιδη κατοικιδίων"));
    }
    public void market(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Τρόφιμα παντοπωλείου"));
    }
    public void personalCleaners(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Είδη προσωπικής υγιεινής"));
    }
    public void nuts(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Ξηροί καρποί και σνακ"));
    }
    public void waters(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Αναψυκτικά και Νερά"));
    }
    public void bread(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Είδη Αρτοζαχαροπλαστείου"));
    }
    public void meat(View view){
        startActivity(new Intent(getApplicationContext(),SelectProducts.class).putExtra("category","Κρεατικά"));
    }


}