package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;

public class InsertProducts extends AppCompatActivity  {

    Spinner productCategories,subCategories;

    EditText productNameEditText,productPriceEditText,productQuantityEditText;

    Uri imageUri;

    ImageView productImageView;

    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
    Button button;
    SharedPreferences preferences;

    String[] subCategoriesItems2;

    String items[];
    String items2[];
    String[] subCategoriesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_products);
        imageUri=null;



        productImageView=findViewById(R.id.imageView1333);

        productNameEditText=findViewById(R.id.editText502);
        productPriceEditText=findViewById(R.id.editText503);
        productQuantityEditText=findViewById(R.id.editText504);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView500);
            textView1.setText("Insert product");
            textView2=findViewById(R.id.textView504);
            textView2.setText("Select Category");
            textView3=findViewById(R.id.textView507);
            textView3.setText("Select subcategory");

            textView4=findViewById(R.id.textView509);
            textView4.setText("Product Name");
            textView5=findViewById(R.id.textView510);
            textView5.setText("Product Price");
            textView6=findViewById(R.id.textView511);
            textView6.setText("Quantity");

            textView7=findViewById(R.id.textView5055);
            textView7.setText("Product Image");

            button=findViewById(R.id.button203);
            button.setText("Insert");

            productPriceEditText.setHint("ex.0.99");
            productNameEditText.setHint("ex:bananas-μπανάνες");

        }
        productCategories=findViewById(R.id.spinner2);
        subCategories=findViewById(R.id.spinner4);


        if(preferences.getString("language","").equals("english")){
            items=new String[]{"Fruits", "Vegetables", "Meat","Bakery","Dairy Products","Alchohol","Detergents","Pet items","Grocery","Personal hygiene","Nuts and snacκs","Soft drinks and water"};
            items2=new String[]{"Φρούτα", "Λαχανικά", "Κρεατικά","Είδη Αρτοζαχαροπλαστείου","Γαλακτοκομικά","Κάβα","Απορρυπαντικά και είδη καθαρισμού","Τροφές και έιδη κατοικιδίων","Τρόφιμα παντοπωλείου","Είδη προσωπικής υγιεινής","Ξηροί καρποί και σνακ","Αναψυκτικά και Νερά"};
        }else{
             items = new String[]{"Φρούτα", "Λαχανικά", "Κρεατικά","Είδη Αρτοζαχαροπλαστείου","Γαλακτοκομικά","Κάβα","Απορρυπαντικά και είδη καθαρισμού","Τροφές και έιδη κατοικιδίων","Τρόφιμα παντοπωλείου","Είδη προσωπικής υγιεινής","Ξηροί καρποί και σνακ","Αναψυκτικά και Νερά"};
             items2=new String[]{};
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        productCategories.setAdapter(adapter);


        productCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if(productCategories.getSelectedItem().toString().equals("Φρούτα") ||productCategories.getSelectedItem().toString().equals("Fruits")){

                    if(preferences.getString("language","").equals("english")){

                        subCategoriesItems= new String[]{"Dried fruits", "Fresh fruits"};
                        subCategoriesItems2=new String[]{"Αποξηραμένα φρόυτα", "Φρέσκα φρούτα"};

                    }else{
                        subCategoriesItems= new String[]{"Αποξηραμένα φρόυτα", "Φρέσκα φρούτα"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Λαχανικά")||productCategories.getSelectedItem().toString().equals("Vegetables")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems = new String[]{"Fresh vegetables", "Frozen vegetables","Prepared salads"};
                        subCategoriesItems2=new String[]{"Φρέσκα λαχανικά", "Κατεψυγμένα λαχανικά","Έτοιμες Σαλάτες"};
                    }else{
                        subCategoriesItems = new String[]{"Φρέσκα λαχανικά", "Κατεψυγμένα λαχανικά","Έτοιμες Σαλάτες"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Κρεατικά")||productCategories.getSelectedItem().toString().equals("Meat")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Fresh meat", "Frozen meat","Cold cuts"};
                        subCategoriesItems2=new String[]{"Φρέσκα κρέατα", "Κατεψυγμένα κρέατα","Αλλαντικά"};
                    }else{
                        subCategoriesItems= new String[]{"Φρέσκα κρέατα", "Κατεψυγμένα κρέατα","Αλλαντικά"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Είδη Αρτοζαχαροπλαστείου")||productCategories.getSelectedItem().toString().equals("Bakery")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Sweets", "Bread","Tortillas"};
                        subCategoriesItems2=new String[]{"Γλυκά", "Ψωμί και αρτοπαρασκευάσματα","Πίτες και τορτίγιες"};
                    }else{
                        subCategoriesItems= new String[]{"Γλυκά", "Ψωμί και αρτοπαρασκευάσματα","Πίτες και τορτίγιες"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Γαλακτοκομικά")||productCategories.getSelectedItem().toString().equals("Dairy Products")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Milk", "Cheeses","Yogurts"};
                        subCategoriesItems2=new String[]{"Γάλατα", "Τυροκομικά","Γιαούρτια"};
                    }else{
                        subCategoriesItems=new String[]{"Γάλατα", "Τυροκομικά","Γιαούρτια"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Κάβα")||productCategories.getSelectedItem().toString().equals("Alchohol")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Wines", "Drinks","Beers"};
                        subCategoriesItems2=new String[]{"Κρασιά", "Ποτά","Μπύρες"};
                    }else{
                        subCategoriesItems=new String[]{"Κρασιά", "Ποτά","Μπύρες"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Απορρυπαντικά και είδη καθαρισμού")||productCategories.getSelectedItem().toString().equals("Detergents")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems=new String[]{"General Purspose Cleaners", "Detergents"};
                        subCategoriesItems2=new String[]{"Καθαριστικά Γενικής Χρήσης", "Απορρυπαντικά"};
                    }else{
                        subCategoriesItems=new String[]{"Καθαριστικά Γενικής Χρήσης", "Απορρυπαντικά"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Τροφές και έιδη κατοικιδίων")||productCategories.getSelectedItem().toString().equals("Pet items")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"For cats", "For dogs","For fish,birds etc"};
                        subCategoriesItems2=new String[]{"Τροφές και είδη για γάτες", "Τροφές και είδη για σκύλους","Τροφές και είδη για ψάρια και πτηνά"};
                    }else{
                        subCategoriesItems=new String[]{"Τροφές και είδη για γάτες", "Τροφές και είδη για σκύλους","Τροφές και είδη για ψάρια και πτηνά"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Τρόφιμα παντοπωλείου")||productCategories.getSelectedItem().toString().equals("Grocery")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Spices", "Canned food","Legumes","Eggs","Oils","Rices"};
                        subCategoriesItems2=new String[]{"Μπαχαρικά", "Κονσέρβες","Όσπρια","Αυγά","Λάδια","Ρύζια"};
                    }else{
                        subCategoriesItems=new String[]{"Μπαχαρικά", "Κονσέρβες","Όσπρια","Αυγά","Λάδια","Ρύζια"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Είδη προσωπικής υγιεινής")||productCategories.getSelectedItem().toString().equals("Personal hygiene")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Pharmacy", "Βody care","Hair care","Οral hygiene","Shaving"};
                        subCategoriesItems2=new String[]{"Είδη φαρμακείου", "Περιποίηση σώματος","Περιποίηση μαλλιών","Στοματική υγιεινή","Είδη ξυρίσματος"};
                    }else{
                        subCategoriesItems=new String[]{"Είδη φαρμακείου", "Περιποίηση σώματος","Περιποίηση μαλλιών","Στοματική υγιεινή","Είδη ξυρίσματος"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Ξηροί καρποί και σνακ")||productCategories.getSelectedItem().toString().equals("Nuts and snacκs")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Crackers", "Chips","Nuts"};
                        subCategoriesItems2=new String[]{"Κράκερς", "Πατατάκια,γαριδάκια","Ξηροί καρποί"};
                    }else{
                        subCategoriesItems=new String[]{"Κράκερς", "Πατατάκια,γαριδάκια","Ξηροί καρποί"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }else if(productCategories.getSelectedItem().toString().equals("Αναψυκτικά και Νερά")||productCategories.getSelectedItem().toString().equals("Soft drinks and water")){


                    if(preferences.getString("language","").equals("english")){
                        subCategoriesItems= new String[]{"Juices", "Soft and energy drinks","Water"};
                        subCategoriesItems2=new String[]{"Χυμοί", "Αναψυκτικά,σόδες και ενεργειακά ποτά","Νερά"};
                    }else{
                        subCategoriesItems=new String[]{"Χυμοί", "Αναψυκτικά,σόδες και ενεργειακά ποτά","Νερά"};
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, subCategoriesItems);
                    subCategories.setAdapter(adapter2);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void uploadtoDatabase(View view){

        if(!(productNameEditText.getText().toString().equals("")||productQuantityEditText.getText().toString().equals("")||productPriceEditText.getText().toString().equals(""))){
            if(imageUri!=null){

                StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Products Images");
                StorageReference fileRef=storageReference.child(productNameEditText.getText().toString()+"image"+".jpg");

                fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {


                                String category=productCategories.getSelectedItem().toString();
                                String subcategory=subCategories.getSelectedItem().toString();
                                if(preferences.getString("language","").equals("english")){
                                    int i= Arrays.asList(items).indexOf(productCategories.getSelectedItem().toString());
                                    category=items2[i];

                                    int y= Arrays.asList(subCategoriesItems).indexOf(subCategories.getSelectedItem().toString());
                                    subcategory=subCategoriesItems2[y];
                                }
                                FirebaseDatabase.getInstance().getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products").child(category).child(subcategory).child(productNameEditText.getText().toString()).child("Quantity").setValue(productQuantityEditText.getText().toString());
                                FirebaseDatabase.getInstance().getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products").child(category).child(subcategory).child(productNameEditText.getText().toString()).child("Price").setValue(productPriceEditText.getText().toString());
                                FirebaseDatabase.getInstance().getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products").child(category).child(subcategory).child(productNameEditText.getText().toString()).child("Image").setValue(uri.toString());
                                if(preferences.getString("language","").equals("english")){
                                    Toast.makeText(getApplicationContext(),"Your product uploaded sucessfully",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Το προϊόν σας καταχωρήθηκε επιτυχώς",Toast.LENGTH_SHORT).show();
                                }


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
        }else{
            if(preferences.getString("language","").equals("english")){
                Toast.makeText(getApplicationContext(),"Please fill out all field to continue",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Συμπληρώστε όλα τα πεδία για να συνεχίσετε",Toast.LENGTH_SHORT).show();
            }
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
            productImageView.setImageURI(imageUri);


        }
    }


    @Override
    public void onBackPressed() {


        finish();
        startActivity(new Intent(getApplicationContext(),AdminMainPage.class));




    }


}