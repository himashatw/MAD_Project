package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {
    Button btnLogOut;
    FirebaseAuth mFireBaseAuth;
    private FirebaseAuth.AuthStateListener mAuthSateListener;
    LinearLayout loutAddNewItem,loutViewItems,loutEditItem,loutAddNewCat,loutEditCat,loutViewCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnLogOut=findViewById(R.id.btnLogOut);

        loutAddNewItem = findViewById(R.id.loutAddItem);
        loutViewItems = findViewById(R.id.loutViewItems);
        loutEditItem = findViewById(R.id.loutEditItem);

        loutAddNewCat = findViewById(R.id.loutAddNewCat);
        loutEditCat = findViewById(R.id.loutEditCat);
        loutViewCat = findViewById(R.id.loutViewCat);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(AdminDashboard.this,LoginAdmin.class);
                startActivity(intent);
            }
        });

        loutAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AdminNewItem.class);
                startActivity(intent);
            }
        });

        loutViewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AdminItems.class);
                startActivity(intent);
            }
        });

        loutEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AdminSelectEdit.class);
                startActivity(intent);
            }
        });

        loutViewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, enquiry2.class);
                startActivity(intent);
            }
        });

        loutEditCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, ChooseCat.class);
                startActivity(intent);
            }
        });

        loutAddNewCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this, AddNewCat.class);
                startActivity(intent);
            }
        });

    }
}