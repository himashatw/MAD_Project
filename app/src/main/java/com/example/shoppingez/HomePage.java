package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.actions.ItemListIntents;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    CardView cardFruits;
    ImageView imgLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cardFruits = findViewById(R.id.cardFruits);

        cardFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListFruits.class);
                startActivity(intent);
            }
        });

        imgLogOut = findViewById(R.id.imageView2);

        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(HomePage.this,LoginAdmin.class);
                startActivity(intent);
            }
        });
    }
}