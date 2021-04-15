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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class EditProductActivity extends AppCompatActivity {

    TextView productNameTextView,textView1,textView2,textView3;
    EditText priceEditText,quantityEditText;
    Button button;
    SharedPreferences preferences;


    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);





        productNameTextView=findViewById(R.id.textView7605);

        productNameTextView.setText(getIntent().getStringExtra("productName"));

        priceEditText=findViewById(R.id.editText5088);
        quantityEditText=findViewById(R.id.editText50499);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView7602);
            textView1.setText("Edit product");
            textView2=findViewById(R.id.textView7608);
            textView2.setText("Price:");
            textView3=findViewById(R.id.textView7604);
            textView3.setText("Quantity:");

            button=findViewById(R.id.button222);
            button.setText("SAVE CHANGES");

            priceEditText.setHint("ex.0.99");

        }


        image=findViewById(R.id.imageView100011);




        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products")
                .child(getIntent().getStringExtra("mainCategory")).child(getIntent().getStringExtra("subCategory"))
                .child(getIntent().getStringExtra("productName"));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                priceEditText.setText(snapshot.child("Price").getValue().toString());
                quantityEditText.setText(snapshot.child("Quantity").getValue().toString());

                //
                String logoUrl=snapshot.child("Image").getValue().toString();
                Picasso.get().load(Uri.parse(logoUrl)).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }

    public void saveChanges(View view){

        if(!(priceEditText.getText().toString().equals("")||quantityEditText.getText().toString().equals(""))){
            FirebaseDatabase.getInstance().getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products")
                    .child(getIntent().getStringExtra("mainCategory")).child(getIntent().getStringExtra("subCategory")).child(getIntent().getStringExtra("productName")).child("Quantity")
                    .setValue(quantityEditText.getText().toString());

            FirebaseDatabase.getInstance().getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("products").child(getIntent().getStringExtra("mainCategory")).child(getIntent().getStringExtra("subCategory"))
                    .child(getIntent().getStringExtra("productName")).child("Price").setValue(priceEditText.getText().toString());

            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"The product updated successfully",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"To προϊόν ενημερώθηκε επιτυχώς",Toast.LENGTH_SHORT).show();
            }

        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"Please fill out all field to continue",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Συμπληρώστε όλα τα πεδία για να συνεχίσετε",Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onBackPressed() {

        finish();
        startActivity(new Intent(getApplicationContext(),ShowProducts.class));




    }
}