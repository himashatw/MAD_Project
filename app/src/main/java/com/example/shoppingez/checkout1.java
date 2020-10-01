package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class checkout1 extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 = "MESSAGE1";
    public static final String EXTRA_MESSAGE2 = "MESSAGE2";

    private EditText address;
    private TextView Bill,Transport,Total;
    private DatabaseReference dbRef;
    private Payment payment;
    FirebaseAuth firebaseAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout1);
        Bill = findViewById(R.id.bill);
        Transport= findViewById(R.id.Delivery);
        Total = findViewById(R.id.Total);

        //Session
       // firebaseAuth=FirebaseAuth.getInstance();

        //*****   Alnkage value eka ganna ona intent ekakin cart eken "bill" ektaa
        int bill =1000;
        int delivery =500;
        int NetTotal = bill+delivery;

        Bill.setText(" "+bill);
        Transport.setText("+  "+delivery);
        Total.setText(""+NetTotal);


        payment = new Payment();

        loadingBar = new ProgressDialog(this);

    }

    public void proceed(View view){

        Intent i = new Intent(checkout1.this , checkout2.class  );

        address = findViewById(R.id.addAddress);
        String addr = address.getText().toString();

        //session
       // String id=firebaseAuth.getCurrentUser().getUid();

        i.putExtra(EXTRA_MESSAGE1 , addr);

        //startActivity(i);

        //  Toast.makeText(this, "You added your Address", Toast.LENGTH_LONG).show();

        //Insert Address to Firebase
        dbRef = FirebaseDatabase.getInstance().getReference().child("Payment");

        try {

            if(TextUtils.isEmpty(address.getText().toString()))
                Toast.makeText(getApplicationContext(), "Please insert your Address", Toast.LENGTH_SHORT).show();
            else{

                //To show loading view
                loadingBar.setTitle("Validate Payment");
                loadingBar.setMessage("Please wait, while checking the credentials. ");
                loadingBar.setCanceledOnTouchOutside(true);
                // loadingBar.wait(2000);
                loadingBar.show();


                //trim() - remove all leading and all spaces
                payment.setAddress(address.getText().toString().trim());
                payment.setTotal(Integer.parseInt(Total.getText().toString().trim()));

                payment.setPid (dbRef.push().getKey());
                dbRef.child(payment.getPid()).setValue(payment);


                Toast.makeText(this, "Successfuly inserted", Toast.LENGTH_SHORT).show();

                //To send the unique id of the payment to next activity
                i.putExtra("pay",payment);
                startActivity(i);

            }

        }catch (NumberFormatException nfe ){
            Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
        }



    }
}