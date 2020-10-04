package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.shoppingez.model.Items;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class AdminNewItem extends AppCompatActivity {

    EditText itmName, itmPrice, itmCategory;
    Button btnSubmit, btnChoose;
    ImageView imgView,imgBack;
    DatabaseReference dbRef;
    StorageReference stRef;
    StorageTask stTask;
    Task uploadTask;
    Items item;
    ProgressBar pBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_item);

        btnSubmit = findViewById(R.id.btnSubmit);
        itmName = findViewById(R.id.inputItemName);
        itmPrice = findViewById(R.id.inputItemPrice);
        itmCategory = findViewById(R.id.inputItemCategory);
        imgView = findViewById(R.id.imgView);
        imgBack = findViewById(R.id.imageView6);
        btnChoose = findViewById(R.id.btnChoose);
        pBar = findViewById(R.id.pBar);

        item = new Items();
        //reference= FirebaseDatabase.getInstance().getReference().child("Items");

        itmName.setError("Enter Item Name");
        itmName.requestFocus();

        itmPrice.setError("Enter Price");
        itmPrice.requestFocus();

        itmCategory.setError("Enter Category");
        itmCategory.requestFocus();

        imgBack.bringToFront();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intent);
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null) {
                    Toast.makeText(getApplicationContext(), "Upload In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imgUri = data.getData();
            //Picasso.with(this).load(imgUri).into(imgView);
            imgView.setImageURI(imgUri);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {

        stRef = FirebaseStorage.getInstance().getReference("Items");

        if (imgUri != null) {

            final StorageReference fileReference = stRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imgUri));

            uploadTask = fileReference.putFile(imgUri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    pBar.setProgress((int) progress);
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pBar.setProgress(0);
                        }

                    }, 500);
                }
            }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        item.setItemName(itmName.getText().toString().trim());
                        item.setItemCategory(itmCategory.getText().toString().trim());
                        item.setItemPrice(Float.parseFloat(itmPrice.getText().toString().trim()));
                        item.setImgUrl(downloadUri.toString());
                        dbRef = FirebaseDatabase.getInstance().getReference().child("Items");
                        String uploadId = dbRef.push().getKey();
                        dbRef.child(uploadId).setValue(item);
                        Toast.makeText(AdminNewItem.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminNewItem.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else {
            Toast.makeText(getApplicationContext(), "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }


}