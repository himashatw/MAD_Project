package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.actions.ItemListIntents;

public class HomePage extends AppCompatActivity {

    CardView cardFruits;

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
    }
}