package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shoppingez.adapter.ImageAdapter;
import com.example.shoppingez.model.Items;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminItems extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    private List<Items> mUploads;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_items);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, getEmail or etc
            String name = user.getEmail();

            Log.d("CurrentUser", "onCreate: "+name); // the name ...
        }

        mRecyclerView = findViewById(R.id.rvImages);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mProgressCircle = findViewById(R.id.progress_circle);

        ivBack = findViewById(R.id.imageView4);
        //ivBack.bringToFront();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }
        });

        mUploads = new ArrayList<>();

        mAdapter = new ImageAdapter(AdminItems.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(AdminItems.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Items");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Items items = postSnapshot.getValue(Items.class);
                    items.setKey(postSnapshot.getKey());
                    mUploads.add(items);
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

    }

    @Override
    public void onEditClick(int position) {
        Items selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        String selectedName = selectedItem.getItemName();
        String selectedUrl = selectedItem.getImgUrl();
        double selectedPrice = selectedItem.getItemPrice();
        String selectedCategory = selectedItem.getItemCategory();
        Log.d("price", "onEditClick: " + selectedPrice);

        Bundle bundle = new Bundle();
        bundle.putString("itmKey", selectedKey);
        bundle.putString("itmName", selectedName);
        bundle.putString("itmCategory", selectedCategory);
        bundle.putDouble("itmPrice", selectedPrice);
        bundle.putString("itmUrl",selectedUrl);

        Intent intent = new Intent(AdminItems.this, AdminEditItem.class);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {
        Items selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImgUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
                Toast.makeText(AdminItems.this, "Item Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}