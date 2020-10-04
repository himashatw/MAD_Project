package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView txtFname,txtLname,txtEmail,txtTelephone;
    Button btnEdit, btnLogout, btnOrders, btnContact;
    DatabaseReference dbRef;
    User user;

    public static final String FirstName = "Fname";
    public static final String LastName = "Lname";
    public static final String Email = "Email";
    public static final String Telephone = "Telephone";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtFname = findViewById(R.id.txtFname);
        txtLname = findViewById(R.id.txtLname);
        txtEmail = findViewById(R.id.txtEmail);
        txtTelephone = findViewById(R.id.txtTelephone);

        btnEdit = findViewById(R.id.btnEdit);
        btnOrders = findViewById(R.id.btnOrders);
        btnContact = findViewById(R.id.btnContact);
        btnLogout = findViewById(R.id.btnLogout);


        dbRef = FirebaseDatabase.getInstance().getReference().child("User/User1");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                txtFname.setText(snapshot.child("firstName").getValue().toString());
                txtLname.setText(snapshot.child("lastName").getValue().toString());
                txtEmail.setText(snapshot.child("email").getValue().toString());
                txtTelephone.setText(snapshot.child("telephone").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent for edit profile page
                Intent i = new Intent(profile.this, Edit_Profile.class);

                String Fname = txtFname.getText().toString();
                String Lname = txtLname.getText().toString();
                String Email = txtEmail.getText().toString();
                String Telephone = txtTelephone.getText().toString();

                i.putExtra("FirstName",Fname);
                i.putExtra("LastName",Lname);
                i.putExtra("Email",Email);
                i.putExtra("Telephone",Telephone);

                startActivity(i);
            }
        });

        

    }
}