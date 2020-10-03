package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shoppingez.model.Items;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ItemProfile extends AppCompatActivity {
    private TextView pro_name, pro_price, qnty;
    private ImageView product_img;
    private ElegantNumberButton quantity_btn;
    private Button addtoCart, buynow;
    private Context context;
    String code;
    String myEmail;
    String subString;


    private static HashMap<String, Object> cartMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);

        pro_name = findViewById(R.id.pro_name);
        pro_price = findViewById(R.id.pro_price);
        qnty = findViewById(R.id.qnty);
        addtoCart = findViewById(R.id.addtoCart);
        product_img = findViewById(R.id.product_img);
        buynow = findViewById(R.id.buynow);
        quantity_btn = findViewById(R.id.quantity_btn);


        code = getIntent().getStringExtra("code");
        myEmail = getIntent().getStringExtra("email");

        String email = myEmail;
        String filename = email;     // full file name
        int iend = filename.indexOf(".");
         subString = "null";
        if (iend != -1) {
            subString = filename.substring(0, iend); //this will give abc

        }

        getProductDetails(code);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddToCartList();

            }
        });


    }

    private void AddToCartList() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calendar =  Calendar.getInstance();


        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());//get the current date in international format

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());//get the current date in international format


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart");

        cartMap =new HashMap<>();
        cartMap.put("pname"         ,pro_name.getText().toString());
        cartMap.put("Pprice"         ,pro_price.getText().toString());
        cartMap.put("pcatogery"         ,qnty.getText().toString());
        cartMap.put("pdate"         ,saveCurrentDate);
        cartMap.put("ptime"         ,saveCurrentTime);
        cartMap.put("pquantity"     ,quantity_btn.getNumber());

        cartListRef.child(subString)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                            final DatabaseReference cartBackup = FirebaseDatabase.getInstance().getReference();

                            Toast.makeText(ItemProfile.this, "Added to Cart to List", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ItemProfile.this, HomePage.class);
                            intent.putExtra("CartProductList", pro_name.getText().toString());/**PUT PRODUCT NAME FOR GETTING ORDER INFPRMATION*/
                            intent.putExtra("CartListQuantity", quantity_btn.getNumber());/**PUT PRODUCT QUANTITY FOR GETTING ORDER INFORMATION*/
                            startActivity(intent);
                        }

                    }
                });


    }

    private void getProductDetails(final String code) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Items");

        productRef.child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    Items items = snapshot.getValue(Items.class);

                    pro_name.setText(items.getItemName());
                    pro_price.setText(String.valueOf(items.getItemPrice()));
                    qnty.setText(items.getItemCategory());
                    Picasso.with(context).load(items.getImgUrl()).into(product_img);




                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    }
