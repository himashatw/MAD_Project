package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class Registration extends AppCompatActivity {

 EditText edFname,edLname,edEmail,edTelephone,edPassword;
 Button btnReg;
 DatabaseReference dbRef;
 User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edFname = findViewById(R.id.edFname);
        edLname = findViewById(R.id.edLname);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edTelephone = findViewById(R.id.edTelephone);

        btnReg = findViewById(R.id.btnReg);

        user = new User();

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("User");

                if (TextUtils.isEmpty(edFname.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Empty First Name", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(edLname.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Empty Last Name", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(edEmail.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Empty Email", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(edTelephone.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Empty Telephone no", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(edPassword.getText().toString()))
                    Toast.makeText(getApplicationContext(),"Empty First Name", Toast.LENGTH_SHORT).show();
                else{
                    user.setFirstName(edFname.getText().toString().trim());
                    user.setLastName(edLname.getText().toString().trim());
                    user.setTelephone(Integer.parseInt(edTelephone.getText().toString().trim()));

                    dbRef.child("User1").setValue(user);

                    Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}