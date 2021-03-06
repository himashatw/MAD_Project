package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoppingez.adapter.ImageAdapter;
import com.example.shoppingez.adapter.ImageAdapter1;
import com.example.shoppingez.model.Items;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ItemsListFruits extends AppCompatActivity implements ImageAdapter1.OnItemClickListener{


    private RecyclerView mRecyclerView;
    private ImageAdapter1 mAdapter;
    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Items> mUploads;
    private ImageView ivBack;
    private ImageButton cartbtn;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list_fruits);

        email=getIntent().getStringExtra("email");


        mRecyclerView = findViewById(R.id.rvImages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mProgressCircle = findViewById(R.id.progress_circle);

        ivBack = findViewById(R.id.imageView4);
        cartbtn=findViewById(R.id.cartbtn);
        //ivBack.bringToFront();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                startActivity(intent);
            }
        });

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent =new Intent(ItemsListFruits.this,Cart_activity.class);
                startActivity(intent);
*/

            }
        });

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter1(ItemsListFruits.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(ItemsListFruits.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Items items = postSnapshot.getValue(Items.class);
                    items.setKey(postSnapshot.getKey());
                    if ((items.getItemCategory().equals("Fruits")) ||(items.getItemCategory().equals("fruits")) ){
                        mUploads.add(items);
                    }

                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //occures when don't have permission to access DB
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), ItemProfile.class);

        Items selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        Log.d("TAGkey", "onItemClick: "+selectedKey);


//        Bundle extras =new Bundle();
//        extras.putString("code",selectedKey);
//        extras.putString("email",email);
//        intent.putExtras(extras);

        intent.putExtra("email",email);
        intent.putExtra("code",selectedKey);

        startActivity(intent);

    }

    @Override
    public void onEditClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }
}

