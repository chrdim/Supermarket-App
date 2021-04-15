package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SelectLogoActivity extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences preferences;


    Button uploadButton,submitButton;

    Uri imageUri;

    SharedPreferences.Editor editor;


    TextView textView1,textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_logo);

        imageView=findViewById(R.id.imageView101);
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        uploadButton=findViewById(R.id.uploadButton101);



        editor=preferences.edit();

        submitButton=findViewById(R.id.continueButton105);


        imageUri=null;

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView101);
            textView1.setText("Finally,");
            textView2=findViewById(R.id.textView100);
            textView2.setText("insert the store's logo");


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

            StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Stores Logos");
            StorageReference fileRef=storageReference.child(preferences.getString("storename","")+"logo"+".jpg");

            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {



                            editor.putString("logo",uri.toString());
                            editor.apply();

                            startActivity(new Intent(getApplicationContext(),ConfirmAdminActivity.class));
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


        if(imageUri==null){
            finish();
            startActivity(new Intent(getApplicationContext(),RegisterAdminImage.class));
        }else{
            String message;
            String title;
            String positive;
            String negative;
            if(preferences.getString("language","").equals("english")){
                message="Your inputs will be lost";
                title="Caution";
                negative="Cancel";
                positive="Back";
            }else {
                message="Τα δεδομενα θα χαθουν";
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
                                    startActivity(new Intent(getApplicationContext(),RegisterAdminImage.class));


                                }
                            }).create().show();
        }



    }
}