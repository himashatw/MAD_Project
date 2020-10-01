package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class checkout2 extends AppCompatActivity {

    EditText textAddress;
    DatabaseReference dbRef;
    Payment payment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout2);

        textAddress=findViewById(R.id.txtAddress);

        Intent i = getIntent();
        final String address = i.getStringExtra(checkout1.EXTRA_MESSAGE1);
        Payment payment = i.getParcelableExtra("pay");

        TextView textView = findViewById(R.id.viewAddress);

        textView.setText(address);

        /// To retreve data from Firebase
        //String PayID = PayID;

      textAddress.setText(payment.getAddress());


    }



    public void change(View view){

        dbRef= FirebaseDatabase.getInstance().getReference();
        //dbRef.child("Enquiry").child("enq1").child("name").setValue(textAddress.getText().toString().trim());
        dbRef.child(payment.getAddress()).setValue(textAddress.getText().toString());
        Toast.makeText(this, "you Changed address", Toast.LENGTH_SHORT).show();
    }


    public void complete(View view){
        String msg= "Our Agent will contact you to confirm the order";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                checkout2.this
        )
                .setSmallIcon(R.drawable.cash)
                .setContentTitle("Order Verification")
                .setContentText(msg)
                .setAutoCancel(true);
//
    /*
        **** This "NotificationActivity" is the activity which goes after click the Notification ****

         Intent intent =new Intent(checkout2.this,NotificationActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message",msg);

         PendingIntent pendingIntent = PendingIntent.getActivity(checkout2.this ,
               0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
    */
        //
        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );

        notificationManager.notify(0 ,builder.build());


    }


    public void CancelOrder(View view){
        Intent i = new Intent(checkout2.this,checkout1.class );
        startActivity(i);
    }
}