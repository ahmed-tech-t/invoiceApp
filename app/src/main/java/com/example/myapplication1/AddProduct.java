package com.example.myapplication1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.Models.ProductModel;
import com.example.myapplication1.databinding.ActivityAddProductBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddProduct extends AppCompatActivity implements View.OnClickListener {

    ActivityAddProductBinding binding;
    ProductModel product;
    Bundle bundle;
    String[] countries;
    String [] section;
    String[] store;


    DatePickerDialog.OnDateSetListener setListener;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        this.setContentView(v);
        setSupportActionBar(binding.toolBarAddPro);
        dbHandler =new DbHandler(this);
        binding.scrollViewAddPro.setOnClickListener(this);
        binding.productList.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setDataToOrigenSpinner();
        setDataToSectionSpinner();
        setDataToStoreSpinner();
        setExpiryDate();
        setProductionDate();
        handleOnUpdate();
    }

    private boolean allIsRight(){
        if(nameIsRight()&&priceIsRight()&&weightIsRight()&&countryIsRight()&&storeIsRight()&&sectionIsRight()&&expiryDateIsRight()&&productionDateDateIsRight()){
            return true;
        }else {
            if(nameIsRight()&&priceIsRight()&&weightIsRight()&&expiryDateIsRight()&&productionDateDateIsRight()){
                if(!countryIsRight()){
                    new AlertDialog.Builder(this)
                            .setTitle("Wrong")
                            .setMessage("please select country first")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }else if(!sectionIsRight()){
                    new AlertDialog.Builder(this)
                            .setTitle("Wrong")
                            .setMessage("please select section first")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }else if(!storeIsRight()){
                    new AlertDialog.Builder(this)
                            .setTitle("Wrong")
                            .setMessage("please select store first")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return false;
                }
                return false;
            }
           return false;
        }
    }
    private void textLengthIsNull(EditText editText){
        if(editText.getText().length()==0){
            editText.setError("Required failed");
        }
    }
    private boolean priceIsRight(){
        textLengthIsNull(binding.etAddProPrice);
        if(binding.etAddProPrice.getError()==null){
            return true;
        }return false;
    }
    private boolean nameIsRight(){
        if(binding.etAddProName.length()<5){
            binding.etAddProName.setError("to short");
        }
        textLengthIsNull(binding.etAddProName);
         if(binding.etAddProName.getError()==null){
            return true;
        }return false;
    }
    private boolean weightIsRight(){
        textLengthIsNull(binding.etAddProWeight);
        if(binding.etAddProWeight.getError()==null){
            return true;
        }return false;
    }
    private boolean countryIsRight(){
        if(binding.etAddProCountryOfOriginSpinner.getSelectedItem().toString() == "Select Country"){
            return false;
        }return true;
    }
    private boolean storeIsRight(){
        if(binding.etAddProStoreSpinner.getSelectedItem().toString() == "Select Store"){
            return false;
        }return true;
    }
    private boolean sectionIsRight(){
        if(binding.etAddProSectionSpinner.getSelectedItem().toString() == "Select Section"){
            return false;
        }return true;
    }
    private boolean expiryDateIsRight(){
        textLengthIsNull(binding.etAddProExpiryDate);
            if(binding.etAddProExpiryDate.getText().length()>0){
            return true;
        }return false;
    }
    private boolean productionDateDateIsRight(){
        textLengthIsNull(binding.etAddProProductionDate);
        if(binding.etAddProProductionDate.getError()==null){
            return true;
        }return false;
    }
    private void getDataFromForm(){
        String name =binding.etAddProName.getText().toString();
        double price =Double.parseDouble(binding.etAddProPrice.getText().toString());
        int code = generateRandomNumber() ;
        String countryOfOrigin = binding.etAddProCountryOfOriginSpinner.getSelectedItem().toString();
        System.out.println(countryOfOrigin);
        String productionDate = binding.etAddProProductionDate.getText().toString();
        String expiryDate = binding.etAddProExpiryDate.getText().toString();
        String storeName = binding.etAddProStoreSpinner.getSelectedItem().toString();
        double weight = Double.parseDouble(binding.etAddProWeight.getText().toString());
        String section = binding.etAddProSectionSpinner.getSelectedItem().toString();
        if(bundle==null){
            product = new ProductModel(code,name,price,countryOfOrigin,productionDate,expiryDate,weight,section,storeName);
        }else if(bundle != null){
            product.setName(name);
            product.setPrice(price);
            product.setProductionDate(productionDate);
            product.setCountryOfOrigin(countryOfOrigin);
            product.setExpiryDate(expiryDate);
            product.setWeight(weight);
            product.setSection(section);
            product.setStoreName(storeName);
        }
    }
    public void saveProduct(View view) {
        if(allIsRight()){
           getDataFromForm();
            if(bundle==null){
                dbHandler.addProduct(product);
                dbHandler.setValueProductQuantityTable(product.getCode());
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else if(bundle != null){
                dbHandler.updateProduct(product);
                Intent intent = new Intent(this,ManageProducts.class);
                startActivity(intent);
            }
        }else{
            Log.w("saved","wrong");
        }
    }
    private void hideKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    @Override
    public void onClick(View view) {
        if(view.getId()==binding.productList.getId()||view.getId()==binding.scrollViewAddPro.getId()){
            hideKeyBoard(view);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(this, ActivityCodeScanner.class);
                    intent.putExtra("addProductId",1);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddProduct.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    public int generateRandomNumber(){
        int min=1000000;
        int max=9999999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        if(isExist())
            generateRandomNumber();

        return random_int;
    }
    public boolean isExist(){
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if(bundle==null){
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }else if(bundle!=null){
                Intent intent = new Intent(this,ManageProducts.class);
                startActivity(intent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
    public void setDataToOrigenSpinner(){
        try {
            countries = new String[]{"Select Country","Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
            binding.etAddProCountryOfOriginSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,countries));
        }catch (Exception e){
         e.printStackTrace();
        }
    }
    public void setDataToSectionSpinner(){
       try {
           section = new String[]{"Select Section","Electronics","Meat","Seafood","Beer and Wine","Health and Beauty","Deli/Prepared Foods"};
           binding.etAddProSectionSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,section));
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    public void setDataToStoreSpinner(){
        try {
            store = new String[]{"Select Store","alsa3y","elrefa3y","el7egazy"};
            binding.etAddProStoreSpinner.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,store));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void handleOnUpdate(){
        try {
            bundle = getIntent().getExtras();
            if(bundle != null) {
                setTitle("Edit Product");
                product = (ProductModel) getIntent().getSerializableExtra("product");
                binding.etAddProName.setText(product.getName());
                binding.etAddProPrice.setText(String.valueOf(product.getPrice()));
                for (int i=0;i<countries.length;i++){
                    if(product.getCountryOfOrigin().equals(countries[i])){
                        binding.etAddProCountryOfOriginSpinner.setSelection(i);
                        break;
                    }
                }
                binding.etAddProProductionDate.setText(product.getProductionDate());
                binding.etAddProExpiryDate.setText(product.getExpiryDate());
                for (int i=0;i<store.length;i++){
                    if(product.getStoreName().equals(store[i])){
                        binding.etAddProStoreSpinner.setSelection(i);
                        break;
                    }
                }
                binding.etAddProWeight.setText(String.valueOf(product.getWeight()));
                for (int i=0;i<section.length;i++){
                    if(product.getSection().equals(section[i])){
                        binding.etAddProSectionSpinner.setSelection(i);
                        break;
                    }
                }
                binding.saveProduct.setText("Update");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setProductionDate(){
        try {
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    binding.etAddProProductionDate.setText(sdf.format(myCalendar.getTime()));
                    binding.etAddProProductionDate.setError(null);
                }
            };

            binding.etAddProProductionDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(AddProduct.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }catch (Exception e){
         e.printStackTrace();
        }
    }
    public void setExpiryDate(){
        try {
            final Calendar myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd/MM/yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    binding.etAddProExpiryDate.setText(sdf.format(myCalendar.getTime()));
                    binding.etAddProExpiryDate.setError(null);
                }

            };

            binding.etAddProExpiryDate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(AddProduct.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}