package com.example.shoppingez;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnquiryViewAdapter extends


        RecyclerView.Adapter<EnquiryViewAdapter.ViewHolder> {

    private ArrayList<Enquiry> enquiry = new ArrayList<>();
    private Context mContext;

    public EnquiryViewAdapter(ArrayList<Enquiry> enquiry, Context mContext) {
        this.enquiry= enquiry;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.enquiry_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Enquiry enq= enquiry.get(position);

        holder.txtEmail.setText(enq.getEmail());
        holder.txtMessage.setText(enq.getMessage());

        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(Intent.ACTION_SEND);
                Intent chooser;


                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{enq.getEmail()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "ShoppingEZ Reply for Enquiry" );
                intent.putExtra(Intent.EXTRA_TEXT, "This is mail body");
                intent.setType("message/frc822");
                chooser = Intent.createChooser(intent,"send email ");

                //startActivity(chooser);
                holder.btnReply.getContext().startActivity(chooser);



            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               final DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference().child("Enquiry").child(String.valueOf(enq.getId()));
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dbRef.removeValue();
                        Intent intent=new Intent(holder.btnDelete.getContext(),enquiry2.class);
                        holder.btnDelete.getContext().startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
    @Override
    public int getItemCount() {
        return enquiry.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtEmail, txtMessage;
        Button btnReply,btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEmail=itemView.findViewById(R.id.emailID);
            txtMessage=itemView.findViewById(R.id.MessageID);
            btnReply=itemView.findViewById(R.id.replyID);
            btnDelete=itemView.findViewById(R.id.DeleteID);

        }
    }


}
