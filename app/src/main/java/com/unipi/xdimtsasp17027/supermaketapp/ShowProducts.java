package com.unipi.xdimtsasp17027.supermaketapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ShowProducts extends AppCompatActivity {

    Spinner productCategories,subCategories;
    ArrayList<View> viewArrayList;

    LinearLayout layout;

    TextView productNameTextView,textView1,textView2,textView3;
    Button button;
    SharedPreferences preferences;

    ImageView deleteImageView,editImageView;


    ArrayList<ImageView> deleteImageViews, editImageViews;
    ArrayList<String>productNames;

    String[] subCategoriesItems2;

    String items[];
    String items2[];
    String[] subCategoriesItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(preferences.getString("language","").equals("english")){
            textView1=findViewById(R.id.textView7600);
            textView1.setText("Products");
            textView2=findViewById(R.id.textView5055);
            textView2.setText("Select category");
            textView3=findViewById(R.id.textView5075);
            textView3.setText("Select subcategory");
            button=findViewById(R.id.button);
            button.setText("Search");


        }

        deleteImageViews =new ArrayList<ImageView>();
        editImageViews =new ArrayList<ImageView>();
        productNames=new ArrayList<String>();

        productCategories=findViewById(R.id.spinner24);
        subCategories=findViewById(R.id.spinner14);

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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void search(View view) {

        String category;
        String subcategory;

        if(preferences.getString("language","").equals("english")){
            int i= Arrays.asList(items).indexOf(productCategories.getSelectedItem().toString());
            category=items2[i];

            int y= Arrays.asList(subCategoriesItems).indexOf(subCategories.getSelectedItem().toString());
            subcategory=subCategoriesItems2[y];
        }else{
             category =productCategories.getSelectedItem().toString();
             subcategory=subCategories.getSelectedItem().toString();
        }


        viewArrayList = new ArrayList<View>();
        layout = findViewById(R.id.linearLayout222);



        layout.removeAllViewsInLayout();
        viewArrayList.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products").child(category).child(subcategory);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot datas : snapshot.getChildren()) {


                    View v = getLayoutInflater().inflate(R.layout.product_row, null, false);

                    productNameTextView=(TextView) v.findViewById(R.id.textView800);

                    deleteImageView=(ImageView)v.findViewById(R.id.imageView207);
                    editImageView=(ImageView) v.findViewById(R.id.imageView203);

                    deleteImageViews.add(deleteImageView);
                    editImageViews.add(editImageView);

                    productNameTextView.setText(datas.getKey());

                    productNames.add(datas.getKey());


                    viewArrayList.add(v);


                }

                if(viewArrayList.size()>0){
                    for (int i = 0; i < viewArrayList.size(); i++) {
                        if(viewArrayList.get(i).getParent() != null) {
                            ((ViewGroup)viewArrayList.get(i).getParent()).removeView(viewArrayList.get(i));
                        }
                        layout.addView(viewArrayList.get(i));
                        String productName=productNames.get(i);
                        editImageViews.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startActivity(new Intent(getApplicationContext(),EditProductActivity.class).putExtra("productName",productName)
                                .putExtra("mainCategory",category).putExtra("subCategory",subcategory));
                            }
                        });

                        deleteImageViews.get(i).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {



                                layout.removeAllViewsInLayout();
                                viewArrayList.clear();

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Supermakets").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("products").child(category).child(subcategory).child(productName);
                                myRef.removeValue();
                                if(preferences.getString("language","").equals("english")){
                                    Toast.makeText(getApplicationContext(),"The product "+productName+" deleted successfully",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"To προϊόν "+productName+" διαγράφηκε επιτυχώς",Toast.LENGTH_SHORT).show();
                                }



                            }

                        });

                    }
                }else{
                    if(preferences.getString("language","").equals("english")){
                        Toast.makeText(getApplicationContext(),"No product yet",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Δεν έχετε καταχωρήσει κάποιο προιόν μέχρι στιγμής",Toast.LENGTH_SHORT).show();
                    }

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
