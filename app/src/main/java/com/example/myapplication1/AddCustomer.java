package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication1.DataBase.DbHandler;
import com.example.myapplication1.Models.CustomerModel;
import com.example.myapplication1.databinding.ActivityAddCustomerBinding;
import com.example.myapplication1.databinding.ActivityNewBillBinding;

import java.util.ArrayList;

public class AddCustomer extends AppCompatActivity implements  View.OnClickListener {
    ActivityAddCustomerBinding binding;
    CustomerModel customer;
    DbHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        this.setContentView(view);
        setSupportActionBar(binding.ToolBar);
        dbHandler =new DbHandler(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add New Customer");
        setConditionsToCustomer();
        binding.conLayout.setOnClickListener(this);
        binding.innerLinerListCus.setOnClickListener(this);
       /* binding.wave.setVelocity(1);
        binding.wave.setProgress(1);
        binding.wave.isRunning();
        binding.wave.setGradientAngle(45);
        binding.wave.setWaveHeight(70);
        binding.wave.setStartColor(R.color.colorPrimary);
        binding.wave.setCloseColor(R.color.whitePurple);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setConditionsToCustomer() {
        NameConditions(binding.etAddCusName);
        AgeConditions(binding.etAddCusAge);
        JopConditions(binding.etAddCusJop);
        GenderConditions(binding.etAddCusGender);
        IDCardConditions(binding.etAddCusIdCard);
        PhoneNumberConditions(binding.etAddCusPhoneNumber);
        AddressConditions(binding.etAddCusAddress);
    }
    //read data from fields
    private void readDataFromView() {
        customer = new CustomerModel();
        customer.setName(binding.etAddCusName.getText().toString());
        customer.setAge(binding.etAddCusAge.getText().toString());
        customer.setGender(binding.etAddCusGender.getText().toString());
        customer.setJop(binding.etAddCusJop.getText().toString());
        customer.setIdCard(binding.etAddCusIdCard.getText().toString());
        customer.setPhoneNumber(binding.etAddCusPhoneNumber.getText().toString());
        customer.setAddress(binding.etAddCusAddress.getText().toString());
    }
    private void NameConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the name");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    editText.setError("Enter the name");
                } else if (s.length() < 12) {
                    editText.setError("Enter the full jop");
                } else if (s.length() > 50) {
                    editText.setError("to long");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void AgeConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the age");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    editText.setError("Enter the age");
                } else if (Integer.parseInt(s.toString()) > 100 || Integer.parseInt(s.toString()) < 18) {
                    editText.setError("invalid age");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void JopConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the jop");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    editText.setError("Enter the jop");
                } else if (s.length() > 30) {
                    editText.setError("to long");
                } else if (s.toString().matches(".*\\d.*")) {
                    editText.setError("jop shouldn't contain any numbers");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void GenderConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the gender");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    editText.setError("Enter the gender");
                } else if (s.toString().equals("M") || s.toString().equals("F") || s.toString().equals("m") || s.toString().equals("f")) {
                } else editText.setError("gender must be 'M' or 'F'");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void IDCardConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the idCard");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 14) {
                    editText.setError("invalid idCard");
                } else if (s.length() < 14) {
                    editText.setError("invalid idCard");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void PhoneNumberConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the phone number");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 11) {
                    editText.setError("Enter the phone number");
                } else if (s.length() < 11) {
                    editText.setError("Enter the phone number");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void AddressConditions(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("Enter the address");
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0) {
                    editText.setError("Enter the address");
                } else if (s.length() < 10) {
                    editText.setError("to short");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private Boolean customerIsRight(){
        boolean customer = false;
        ifPressedAndFieldsAreEmpty(binding.etAddCusName, binding.etAddCusAge, binding.etAddCusGender, binding.etAddCusJop, binding.etAddCusIdCard, binding.etAddCusPhoneNumber, binding.etAddCusAddress);
        if (binding.etAddCusName.getError() == null
                && binding.etAddCusAge.getError() == null
                && binding.etAddCusGender.getError() == null
                && binding.etAddCusJop.getError() == null
                && binding.etAddCusIdCard.getError() == null
                && binding.etAddCusPhoneNumber.getError() == null
                && binding.etAddCusAddress.getError() == null) {
            customer = true;
        }else customer =false;
        return customer;
    }
    private void ifPressedAndFieldsAreEmpty(EditText etName, EditText etAge, EditText etGender, EditText etJop, EditText etIdCard, EditText etPhoneNumber, EditText etAddress) {
        if (etName.getText().length() == 0) {
            etName.setError("Enter the name");
        }
        if (etAge.getText().length() == 0) {
            etAge.setError("Enter the age");
        }
        if (etGender.getText().length() == 0) {
            etGender.setError("Enter the gender");
        }
        if (etJop.getText().length() == 0) {
            etJop.setError("Enter the jop");
        }
        if (etIdCard.getText().length() == 0) {
            etIdCard.setError("Enter the idCard");
        }
        if (etPhoneNumber.getText().length() == 0) {
            etPhoneNumber.setError("Enter the phone number");
        }
        if (etAddress.getText().length() == 0) {
            etAddress.setError("Enter the Address");
        }

    }
    @Override
    public void onClick(View v) {
        if(binding.innerLinerListCus.getId()==v.getId()||binding.conLayout.getId()==v.getId()||binding.conLayout.getId()==v.getId()){
            hideKeyBoard(v);
         }
    }
    private void hideKeyBoard(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void cuSave(View view) {
        if(customerIsRight()){
            readDataFromView();
            dbHandler.addCustomer(customer);

            Log.e("true","save");
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else Log.e("false","failed");
    }
}