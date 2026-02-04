package com.finalyearapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    GridView gridView;
    String[] nameArray = {
            "Minutes",
            "Mobiles & Tablets",
            "Fashion",
            "Electronics",
            "TVs & Appliances",
            "Home & Furniture",
            "Flight Bookings",
            "Beauty, Food..",
            "Grocery"
    };

    String[] imageArray = {
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/e00302d428f5c7be.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/5f2ee7f883cdb774.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/ff559cb9d803d424.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/af646c36d74c4be9.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/e90944802d996756.jpg?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/1788f177649e6991.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/3c647c2e0d937dc5.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/b3020c99672953b9.png?q=100",
            "https://rukminim2.flixcart.com/fk-p-flap/64/64/image/e730a834ad950bae.png?q=100"
    };

    ArrayList<CategoryList> arrayList;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_category);

        gridView = findViewById(R.id.category_listview);

        //ArrayAdapter adapter = new ArrayAdapter(CategoryActivity.this, android.R.layout.simple_list_item_1,nameArray);




        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            CategoryList list = new CategoryList();
            list.setName(nameArray[i]);
            list.setImage(imageArray[i]);
            arrayList.add(list);
        }
        //CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,nameArray,imageArray);
        CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,arrayList);
        gridView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CategoryActivity.this, nameArray[i], Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}