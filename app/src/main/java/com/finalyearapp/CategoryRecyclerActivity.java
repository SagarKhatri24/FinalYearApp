package com.finalyearapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    int[] idArray = {1,2,3,4,5,6,7,8,9};
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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        db = openOrCreateDatabase("FinalApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);


        recyclerView = findViewById(R.id.category_recycler);

        //recyclerView.setLayoutManager(new LinearLayoutManager(CategoryRecyclerActivity.this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        for(int i=0; i<nameArray.length; i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkCategory, null);

            if(cursor.getCount()>0){
                // Category Already Exists
            }
            else{
                String insertCategory = "INSERT INTO category VALUES (null, '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }


        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            CategoryList list = new CategoryList();
            list.setId(idArray[i]);
            list.setName(nameArray[i]);
            list.setImage(imageArray[i]);
            arrayList.add(list);
        }
        //CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,nameArray,imageArray);
        CategoryRecyclerAdapter adapter = new CategoryRecyclerAdapter(CategoryRecyclerActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}