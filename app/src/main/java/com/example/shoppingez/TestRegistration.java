package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TestRegistration extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    TextView SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        SignIn = findViewById(R.id.txtSignIn);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter an Email");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if ((email.isEmpty()) && (pwd.isEmpty())) {
                    Toast toast = Toast.makeText(TestRegistration.this, "Fields are empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Validating Begins", Toast.LENGTH_SHORT);
                    toast.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(TestRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast toast=Toast.makeText(getApplicationContext(),"SignUp Unsuccessfull, Try again",Toast.LENGTH_SHORT);
                                toast.show();

                            } else {
                                startActivity(new Intent(TestRegistration.this,AdminDashboard.class));

                            }
                        }
                    });
                }
                else{
                    Toast toast = Toast.makeText(TestRegistration.this,"Error Occurred ",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestRegistration.this,LoginAdmin.class);
                startActivity(intent);
            }
        });

    }
}