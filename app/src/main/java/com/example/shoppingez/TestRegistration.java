package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TestRegistration extends AppCompatActivity {

    EditText emailId, password,  edFname,edLname,edTelephone;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference dbRef;
    TextView SignIn;
    ProgressDialog progressDialog;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_registration);

        mFirebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        user = new User();

        emailId = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        SignIn = findViewById(R.id.txtSignIn);

        edFname = findViewById(R.id.edFname);
        edLname = findViewById(R.id.edLname);
        edTelephone = findViewById(R.id.edTelephone);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                    Toast toast = Toast.makeText(TestRegistration.this, "Fields are empty", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Validating", Toast.LENGTH_SHORT);
                    toast.show();
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(TestRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast toast=Toast.makeText(getApplicationContext(),"SignUp Unsuccessfull, Try again",Toast.LENGTH_SHORT);
                                toast.show();

                            } else {
                                //Creating User Child in RealTime DB
                                user.setFirstName(edFname.getText().toString().trim());
                                user.setLastName(edLname.getText().toString().trim());
                                user.setTelephone(Integer.parseInt(edTelephone.getText().toString().trim()));

                                Log.d("TAGuser", "onComplete: "+user.getFirstName()+" "+user.getLastName()+ " "+user.getTelephone());

                                String fullEmail = email;
                                String filename = fullEmail;

                                int iend = filename.indexOf(".");
                                String subString = "ChildEmailNull";
                                if (iend != -1){
                                    subString = filename.substring(0,iend);

                                }
                                Log.d("TagEmail", "onComplete: "+subString);


                                dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                dbRef.child(subString).setValue(user);

                                startActivity(new Intent(TestRegistration.this,HomePage.class));

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