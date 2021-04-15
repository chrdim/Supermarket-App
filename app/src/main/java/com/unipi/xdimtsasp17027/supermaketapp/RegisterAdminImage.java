package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterAdminImage extends AppCompatActivity {


    ImageView imageView;
    SharedPreferences preferences;


    Button uploadButton,submitButton;

    Uri imageUri;

    SharedPreferences.Editor editor;
    TextView textView1,textView2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin_image);

        imageView=findViewById(R.id.imageView8);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        uploadButton=findViewById(R.id.uploadButton);


        submitButton=findViewById(R.id.continueButton5);



        editor=preferences.edit();


        imageUri=null;

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView15);
            textView1.setText("Also,");
            textView2=findViewById(R.id.textView16);
            textView2.setText("insert an image of your products");


            submitButton.setText("Submit");

            uploadButton.setText("Find Image");

        }


    }

    public void chooseImage(View view){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            imageUri=data.getData();
            imageView.setImageURI(imageUri);


        }
    }

    public void nextActivity(View view){

        if(imageUri!=null){

            StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("Stores Images");
            StorageReference fileRef=storageReference.child(preferences.getString("storename","")+"image"+".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {



                            editor.putString("image",uri.toString());
                            editor.apply();


                            startActivity(new Intent(getApplicationContext(),SelectLogoActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"You have to insert an image to continue",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Για να συνεχίσετε θα πρέπει να εισάγετε μία εικόνα",Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onBackPressed() {



        finish();
        startActivity(new Intent(getApplicationContext(),RegisterAdminTimeAndMinimumOrder.class));






    }

}