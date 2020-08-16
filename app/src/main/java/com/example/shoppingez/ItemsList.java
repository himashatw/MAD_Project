package com.example.shoppingez;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ItemsList extends AppCompatActivity {

   /* ListView listView;
    String mTitle[]={"Apple","Mango","Orange","Grapes"};
    String description[]={"Rs. 150.0","Rs.120.0","Rs.200.0","Rs.250.0"};
    int images[]={R.drawable.apples,R.drawable.mango,R.drawable.orange,R.drawable.grapes};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        /*listView=findViewById(R.id.list_view);

        MyAdapter myAdapter=new MyAdapter(this,mTitle,description,images);
        listView.setAdapter(myAdapter);*/

    }

    /*class MyAdapter extends ArrayAdapter<String>
    {
        Context contex;
        String rTitle[];
        String rDescription[];
        int rImages[];

        MyAdapter (Context c,String title[],String description[],int images[])
        {
            super(c,R.layout.row_items,R.id.textView1,title);
            this.contex=c;
            this.rTitle=title;
            this.rDescription=description;
            this.rImages=images;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=layoutInflater.inflate(R.layout.row_items,parent,false);
            ImageView images=row.findViewById(R.id.image_view);
            TextView myTitle=row.findViewById(R.id.textView1);

            TextView myDescription=row.findViewById(R.id.textView2);

            images.setImageResource(rImages[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return super.getView(position, convertView, parent);
        }
    }*/
}

