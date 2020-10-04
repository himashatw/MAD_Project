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

    CardView cardFruits, cardVegetables, cardBeverages, cardMeat, cardRice, cardSnacks;
    ImageView imgLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cardFruits = findViewById(R.id.cardFruits);
        cardVegetables = findViewById(R.id.cardVegetables);
        cardBeverages = findViewById(R.id.cardBeverages);
        cardMeat = findViewById(R.id.cardMeat);
        cardRice = findViewById(R.id.cardRice);
        cardSnacks = findViewById(R.id.cardSnacks);

        cardFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListFruits.class);
                startActivity(intent);
            }
        });

        cardVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListVegetables.class);
                startActivity(intent);
            }
        });

        cardBeverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListBevarages.class);
                startActivity(intent);
            }
        });

        cardMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListMeat.class);
                startActivity(intent);
            }
        });

        cardRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListRice.class);
                startActivity(intent);
            }
        });

        cardSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ItemsListSnacks.class);
                startActivity(intent);
            }
        });

        imgLogOut = findViewById(R.id.imageView2);

        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomePage.this, LoginAdmin.class);
                startActivity(intent);
            }
        });
    }
}