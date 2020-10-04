package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoppingez.model.Cart;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cart_activity extends AppCompatActivity {

    private RecyclerView recylarView;
    LinearLayoutManager layoutManager;

    private Button BuyNowbtn;
    private TextView getTotalamount;
    private int AllProductPrice;
    private int calcTotPrice;
    private ArrayList<Cart_activity> cartList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recylarView = (RecyclerView) findViewById(R.id.recycler_menu_for_cart);
        recylarView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recylarView.setLayoutManager(layoutManager);

        BuyNowbtn = (Button) findViewById(R.id.BuyNowbtn);
        getTotalamount = (TextView) findViewById(R.id.cart_total);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef
                        .child("UserView"), Cart.class)
                .build();


        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.pname.setText(model.getPname());
                holder.price.setText(String.valueOf(model.getPrice()));
                holder.pquantity.setText(model.getPquantity());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recylarView.setAdapter(adapter);
        adapter.startListening();

    }
}

