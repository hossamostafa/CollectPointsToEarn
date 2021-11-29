package com.koshy.collectpoints;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
   EditText UserNAmeField , PasswordField;
   Button login ;
   TextView register_now , forget_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        ClickLogin();
        ClickRegisterNow();
        ClickForgetPassword();
    }

    private void ClickForgetPassword() {
    forget_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),ForgetPassword.class);
            startActivity(intent);
        }
    });
    }

    private void ClickRegisterNow() {
    register_now.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),Register.class);
            startActivity(intent);
        }
    });
    }

    private void ClickLogin() {
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkable();
        }
    });
    }

    private void checkable() {
    if(UserNAmeField.getText().toString().isEmpty())
        Toast.makeText(getApplicationContext(),"UserName is notFound",Toast.LENGTH_LONG).show();
    else if (PasswordField.getText().toString().isEmpty())
        Toast.makeText(getApplicationContext(),"Password is notFound",Toast.LENGTH_LONG).show();
    }

    private void init(){
        UserNAmeField = findViewById(R.id.email_username_field);
        PasswordField = findViewById(R.id.password_log);
        login = findViewById(R.id.btn_log);
        register_now = findViewById(R.id.register_now);
        forget_password = findViewById(R.id.forget_pass);
    }
}