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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorTreeAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class CustomerSupermarketSelectionActivity extends AppCompatActivity {

    TextView welcomeTextView;
    private FirebaseAuth mAuth;

    ArrayList<View> viewArrayList;

    LinearLayout layout;

    TextView superMarketNameTextView;
    ImageView storeImageView,logoImageview;


    TextView textview;

    ArrayList<TextView>textViews;
    ArrayList<ImageView>imageViews;
    ArrayList<String>storeids;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_supermarket_selection);

        db = openOrCreateDatabase("shop2", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS shop2(productName TEXT,productPrice TEXT,productQuantity TEXT,category TEXT,subCategory TEXT,storeIdofProduct TEXT)");
        Cursor cursor3 = db.rawQuery("DELETE  FROM shop2", null);
        if (cursor3.getCount() >= 0) {
            StringBuilder builder2 = new StringBuilder();
            while (cursor3.moveToNext()) {

                builder2.append("-----------------------------------\n");
            }

        }

        welcomeTextView=findViewById(R.id.textView71);

        mAuth = FirebaseAuth.getInstance();

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        editor=preferences.edit();


        viewArrayList = new ArrayList<View>();



        imageViews=new ArrayList<ImageView>();
        textViews=new ArrayList<TextView>();
        storeids=new ArrayList<String>();


        textview=findViewById(R.id.textView71);
        if(preferences.getString("language","").equals("english")){
            textview.setText("Choose the supermarket you want");

        }

        layout = findViewById(R.id.linearLayout2);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Supermakets");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot datas : snapshot.getChildren()) {



                    View v = getLayoutInflater().inflate(R.layout.supermaket_row, null, false);



                    String storeName = datas.child("name").getValue().toString();


                    storeImageView=(ImageView) v.findViewById(R.id.imageView70);

                    logoImageview=(ImageView) v.findViewById(R.id.imageView300);




                    superMarketNameTextView=(TextView) v.findViewById(R.id.textView72);






                    superMarketNameTextView.setText(storeName);



                    String imageUrl=datas.child("image").getValue().toString();
                    Picasso.get().load(Uri.parse(imageUrl)).into(storeImageView);

                    String logoUrl=datas.child("logo").getValue().toString();
                    Picasso.get().load(Uri.parse(logoUrl)).into(logoImageview);

                    textViews.add(superMarketNameTextView);
                    imageViews.add(storeImageView);

                    storeids.add(datas.getKey());


                    viewArrayList.add(v);


                }


                for (int i = 0; i < viewArrayList.size(); i++) {
                    if(viewArrayList.get(i).getParent() != null) {
                        ((ViewGroup)viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                    }

                    layout.addView(viewArrayList.get(i));
                    String storeid=storeids.get(i);

                    textViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            editor.putString("storeid",storeid);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(),ActivityProductsCategories.class));
                        }
                    });

                    imageViews.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            editor.putString("storeid",storeid);
                            editor.apply();
                            startActivity(new Intent(getApplicationContext(),ActivityProductsCategories.class));
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
        startActivity(new Intent(this,SelectAddressAcitvity.class));


    }


}