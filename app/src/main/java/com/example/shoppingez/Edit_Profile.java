package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.UnknownServiceException;

public class Edit_Profile extends AppCompatActivity {

    DatabaseReference dbRef;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);

        Intent i = getIntent();
        final String fname = i.getStringExtra("FirstName");
        final String lname = i.getStringExtra("LastName");
        final String email = i.getStringExtra("Email");
        final String telephone = i.getStringExtra("Telephone");

        final EditText editFname = findViewById(R.id.editFname);
        final EditText editLname = findViewById(R.id.editLname);
        final EditText editEmail = findViewById(R.id.editEmail);
        final EditText editTelephone = findViewById(R.id.editTelephone);

        final User user = new User();

        btnUpdate = findViewById(R.id.btnUpdate);

        editFname.setText(fname);
        editLname.setText(lname);
        editEmail.setText(email);
        editTelephone.setText(telephone);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setFirstName(editFname.getText().toString().trim());
                user.setLastName(editLname.getText().toString().trim());
                user.setTelephone(Integer.parseInt(editTelephone.getText().toString().trim()));

                String fullEmail = email;
                String filename = fullEmail;

                int iend = filename.indexOf(".");
                String subString = "ChildEmailNull";
                if (iend != -1) {
                    subString = filename.substring(0, iend);

                }

                dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
                dbRef.child(subString).setValue(user);

                Toast.makeText(getApplicationContext(), "Profile Details Updated", Toast.LENGTH_SHORT).show();

            }


        });
    }

}