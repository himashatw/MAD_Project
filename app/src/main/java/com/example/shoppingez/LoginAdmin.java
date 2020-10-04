package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAdmin extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn;
    ImageView imgBack;
    FirebaseAuth mFirebaseAuth;
    TextView SignUp;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String myEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.btnLogin);
        SignUp = findViewById(R.id.txtSignUp);

        imgBack = findViewById(R.id.imageView7);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),HomePage.class);
                startActivity(intent);
            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireBaseUser = mFirebaseAuth.getCurrentUser();

                if (mFireBaseUser != null) {
                    Toast toast = Toast.makeText(LoginAdmin.this, "You are logged in ", Toast.LENGTH_SHORT);
                    toast.show();
                     myEmail = mFireBaseUser.getEmail();
                    Log.d("firebaseuser", "onAuthStateChanged: " + myEmail);
                    if (myEmail.equals("himashatw@gamil.com")) {

                        Intent intent = new Intent(LoginAdmin.this, AdminDashboard.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(LoginAdmin.this, HomePage.class);
                        intent.putExtra("email",myEmail);
                        startActivity(intent);
                    }

                } else {
                    Toast toast = Toast.makeText(LoginAdmin.this, "Please Login ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter an Email");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if ((email.isEmpty()) && (pwd.isEmpty())) {
                    Toast toast = Toast.makeText(LoginAdmin.this, "Fields are empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {

                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginAdmin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Loging Error Please try again", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                if (email.equals("himashatw@gmail.com")) {

                                    Intent intent = new Intent(LoginAdmin.this, AdminDashboard.class);
                                    startActivity(intent);

                                } else {
                                    Intent intent = new Intent(LoginAdmin.this, HomePage.class);
                                    intent.putExtra("email",myEmail);
                                    startActivity(intent);
                                }
                            }
                        }
                    });

                } else {

                    if (email.equals("himashatw@gmail.com")) {

                        Intent intent = new Intent(LoginAdmin.this, AdminDashboard.class);
                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(LoginAdmin.this, HomePage.class);
                        intent.putExtra("email",myEmail);
                        startActivity(intent);
                    }

                }

            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginAdmin.this, TestRegistration.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}