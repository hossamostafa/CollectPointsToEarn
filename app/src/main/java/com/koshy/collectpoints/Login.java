package com.koshy.collectpoints;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {
    @BindView(R.id.newRegister)
    TextView newRegister;
    @BindView(R.id.loginBtn)
    AppCompatButton loginBtn;
    @BindView(R.id.email_username_field)
    AppCompatEditText emailUsernameField;
    @BindView(R.id.password)
    AppCompatEditText password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        loginBtn.setOnClickListener(v->{
            String email = emailUsernameField.getText().toString();
            String pass = password.getText().toString();

            if (!email.matches(emailPattern)){
                emailUsernameField.setError("invalid email");
            }else if (pass.isEmpty()){
                password.setError("empty");
            }else {
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("hossam", "login : " + task.getResult());
                        if(task.isSuccessful()){
                            Log.d("hossam", "login isSuccessful: " + task.getResult());
                        }else if (task.isComplete()){

                            Log.d("hossam", "login isComplete: " + task.getResult());
                        }
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("isRegistered" , true);
                        intent.putExtra("userId" , authResult.getUser().getUid());
                        startActivity(intent);
                        Log.d("hossam", "login : onSuccess" + authResult.getUser().getUid());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("hossam", "login e : " + e.getMessage());
                    }
                });
            }


        });

        newRegister.setOnClickListener(v ->{
            Intent intent = new Intent(Login.this, Register.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}