package com.koshy.collectpoints;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
   EditText FirstName , UserName , Email , Password , ConfirmPassword ;
   Button regestir;
   CheckBox term;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        ClickRegister();

    }

    private void ClickRegister() {
     regestir.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             cheackable();
         }
     });
   }

    private boolean cheackable() {
    if(FirstName.getText().toString().isEmpty())
    {
        Toast.makeText(getApplicationContext(),"FirstName is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if (UserName.getText().toString().isEmpty())
    {
        Toast.makeText(getApplicationContext(),"UserName is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if (Email.getText().toString().isEmpty())
    {
        Toast.makeText(getApplicationContext(),"Email is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if(Password.getText().toString().isEmpty())
    {
        Toast.makeText(getApplicationContext(),"Password is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if(ConfirmPassword.getText().toString().isEmpty())
    {
        Toast.makeText(getApplicationContext(),"ConfirmPassword is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if (!term.isChecked())
    {
        Toast.makeText(getApplicationContext(),"terms is notFound",Toast.LENGTH_LONG).show();
        return false;
    }
    else if (!ConfirmPassword.getText().toString().equals(Password.getText().toString()))
    {
        Toast.makeText(getApplicationContext(),"ConfirmPassword is Wrong",Toast.LENGTH_LONG).show();
        return false;
    }
        return true;
    }

    private void init(){
        FirstName = findViewById(R.id.firstName);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password_reg);
        ConfirmPassword = findViewById(R.id.confirm_password);
        regestir = findViewById(R.id.btn_reg);
        term = findViewById(R.id.terms);
    }
}