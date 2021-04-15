package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ConfirmAdminActivity extends AppCompatActivity implements LocationListener {

    TextView emailTextView,storeNameTextView;

    SharedPreferences preferences;
    private FirebaseAuth mAuth;
    ImageView storeImageView,logoImageview;
    LocationManager locationManager;

    double x, y;
    int signUpTriesCounter;

    TextView textView1,textView2,textView3;
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_admin);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        emailTextView=findViewById(R.id.textView34);
        storeNameTextView=findViewById(R.id.textView36);


        mAuth = FirebaseAuth.getInstance();


        emailTextView.setText(preferences.getString("email",""));
        storeNameTextView.setText(preferences.getString("storename",""));


        storeImageView =findViewById(R.id.imageView20);
        Picasso.get().load(Uri.parse(preferences.getString("image",""))).into(storeImageView);

        logoImageview =findViewById(R.id.imageView200);
        Picasso.get().load(Uri.parse(preferences.getString("logo",""))).into(logoImageview);





        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        x=0;
        y=0;
        signUpTriesCounter =0;

        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView30);
            textView1.setText("Confirm Page");
            textView2=findViewById(R.id.textView32);
            textView2.setText("Store name:");
            textView3=findViewById(R.id.textView33);
            textView3.setText("Image:");

            button1=findViewById(R.id.confirmButton);
            button1.setText("Confirm");

        }



    }

    public void signUp(View view){

        //έλεγχος για permission προσδιορισμού τοποθεσίας
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);


        } else {
            if(!(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))){

                String message;
                String title;
                String positive;
                String negative;
                if(preferences.getString("language","").equals("english")){
                    message="Your location found via GPS.Please activate GPS and try again";
                    title="Error";
                    negative="Cancel";
                    positive="GPS Settings";
                }else {
                    message="Η τοποθεσία σας εντοπίζεται μέσω GPS.Παρακαλώ ενεργοποιήστε το και προσπαθείστε ξανά";
                    title="Σφάλμα";
                    negative="Άκυρο";
                    positive="Ρυθμίσεις GPS";
                }
                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setTitle(title)
                        .setCancelable(true)
                        .setNegativeButton(negative, null)
                        .setPositiveButton(positive,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                    }
                                }).create().show();

            }else{

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


                if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                    x = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                    y = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                    mAuth.createUserWithEmailAndPassword(preferences.getString("email", ""), preferences.getString("password", ""))
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        setStoreNameandImage();
                                        saveLocationAndNameInFirebase(x,y);

                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    if(preferences.getString("language","").equals("english")){
                        Toast.makeText(getApplicationContext(),"Your location was not found.Please change position in your place to find signal and try again",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Το στίγμα σας δεν εντοπίστηκε.Παρακαλώ αλλάξτε θέση στον χώρο για να βρείτε σήμα και προσπαθείστε ξανά.",Toast.LENGTH_SHORT).show();
                    }

                }


            }

        }


    }


    public void setStoreNameandImage(){


        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder()
                .setDisplayName(preferences.getString("storename","")+"--->"+"admin")
                .setPhotoUri(Uri.parse(preferences.getString("image","")))
                .build();







        mAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signIn();
            }
        });

    }



    public void signIn(){
        mAuth.signInWithEmailAndPassword(preferences.getString("email",""),
                preferences.getString("password","")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //τον μεταφέρουμε στο επόμενο Activity
                startActivity(new Intent(getApplicationContext(),AdminMainPage.class));
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 2: {
                //αφού δώσει άδεια προσδιορισμού τοποθεσίας
                if (grantResults.length > 0) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

                        String message;
                        String title;
                        String positive;
                        String negative;
                        if(preferences.getString("language","").equals("english")){
                            message="Your location found via GPS.Please activate GPS and try again";
                            title="Error";
                            negative="Cancel";
                            positive="GPS Settings";
                        }else {
                            message="Η τοποθεσία σας εντοπίζεται μέσω GPS.Παρακαλώ ενεργοποιήστε το και προσπαθείστε ξανά";
                            title="Σφάλμα";
                            negative="Άκυρο";
                            positive="Ρυθμίσεις GPS";
                        }
                        new AlertDialog.Builder(this)
                                .setMessage(message)
                                .setTitle(title)
                                .setCancelable(true)
                                .setNegativeButton(negative, null)
                                .setPositiveButton(positive,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                            }
                                        }).create().show();

                    }else{
                        //σε περίπτωση που ο χρήστης έχει δώσει άδεια
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


                        if (locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
                            x = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                            y = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                            mAuth.createUserWithEmailAndPassword(preferences.getString("email", ""), preferences.getString("password", ""))
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                setStoreNameandImage();
                                                saveLocationAndNameInFirebase(x,y);

                                            } else {
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
                            if(preferences.getString("language","").equals("english")){
                                Toast.makeText(getApplicationContext(),"Your location was not found.Please change position in your place to find signal and try again",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Το στίγμα σας δεν εντοπίστηκε.Παρακαλώ αλλάξτε θέση στον χώρο για να βρείτε σήμα και προσπαθείστε ξανά.",Toast.LENGTH_SHORT).show();
                            }
                        }



                    }


                }
                //αφού αρνηθεί άδεια προσδιορισμού τοποθεσίας
                else{
                    if(preferences.getString("language","").equals("english")){
                        Toast.makeText(getApplicationContext(),"In order to continue your location has to saved,so please give your permission",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Για να συνεχίσετε θα πρέπει να αποθηκευτεί το στίγμα σας για αυτό θα η άδεια σας είναι απαραίτητη.",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            }

        }
    }


    public void saveLocationAndNameInFirebase(double x, double y){

        HashMap<String, Object> newPost = new HashMap<String,Object>();

        newPost.put("longitude",x);
        newPost.put("latitude",y);



        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("location").setValue(newPost);
        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("name").setValue(preferences.getString("storename",""));
        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("image").setValue(preferences.getString("image",""));
        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("logo").setValue(preferences.getString("logo",""));
        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("time").setValue(preferences.getString("time",""));
        FirebaseDatabase.getInstance().getReference().child("Supermakets").child(mAuth.getCurrentUser().getUid()).child("minimum order").setValue(preferences.getString("minimumorder",""));



    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {

        StorageReference photoRef=FirebaseStorage.getInstance().getReferenceFromUrl(getIntent().getStringExtra("image"));
        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                finish();
                startActivity(new Intent(getApplicationContext(),SelectLogoActivity.class));
            }
        });






    }


}