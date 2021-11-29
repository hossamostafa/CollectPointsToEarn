package com.koshy.collectpoints;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register extends AppCompatActivity {
    @BindView(R.id.registerBtn)
    Button registerBtn;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.confirm_password)
    EditText confirm_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;

    String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        registerBtn.setOnClickListener(v->{
            registerNow();
        });

    }

    private void registerNow(){
            String fName = firstName.getText().toString();
            String uName = userName.getText().toString();
        String emailString = email.getText().toString();
        String pass = password.getText().toString();
        String passAgain = confirm_password.getText().toString();

        if (!emailString.matches(emailPattern)){
            email.setError("invalid email");
        }else if (pass.isEmpty() || passAgain.isEmpty()){
            password.setError("empty");
        }else if (!pass.equals(passAgain)){
            confirm_password.setError("not match");
        }else if(fName.trim().length() < 2){
            firstName.setError("invalid first name");
        }else if(uName.trim().length() < 2){
            userName.setError("invalid username");
        }else {
            mAuth.createUserWithEmailAndPassword(emailString, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Log.d("hossam", "dddddd" + task.getResult());
                    uId = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = db.collection("userData").document(uId);
                    Map<String,Object> user = new HashMap<>();
                    user.put("firstName", fName);
                    user.put("username", uName);
                    user.put("points", "0");
                    documentReference.set(user).addOnSuccessListener(unused -> {
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    });
                }
            }).addOnFailureListener(e -> {
                Log.d("hossam", "eeeeee" + e.getMessage());
            });
        }
    }
}