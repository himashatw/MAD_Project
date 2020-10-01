package com.example.shoppingez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class enquiry1 extends AppCompatActivity {

    EditText email, message;
    DatabaseReference dbRef;
    Enquiry enq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry1);
        email = findViewById(R.id.EnterEmail);
        message = findViewById(R.id.EnterMessage);
        Button btn=findViewById(R.id.send);

        enq = new Enquiry();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbRef = FirebaseDatabase.getInstance().getReference().child("Enquiry");

                try {

                    if(TextUtils.isEmpty(email.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Enter Email !!!", Toast.LENGTH_SHORT).show();

                    else if (TextUtils.isEmpty(message.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Empty Message !!!",Toast.LENGTH_SHORT).show();
                    else{

                        //trim() - remove all leading and all spaces
                        enq.setEmail(email.getText().toString().trim());
                        enq.setMessage(message.getText().toString().trim());

                        enq.setId( dbRef.push().getKey());
                        dbRef.child(enq.getId()).setValue(enq);

                        Toast.makeText(getApplicationContext(), "Successfuly send", Toast.LENGTH_LONG).show();
                        //If we dont use clearControl() method after we insert data , data still appering on the form.
                        clearControls();

                    }

                }catch (NumberFormatException nfe ){
                    Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    //This method use to when we insert data we need to clear widgets
    private void clearControls(){
        email.setText("");
        message.setText("");

    }
}