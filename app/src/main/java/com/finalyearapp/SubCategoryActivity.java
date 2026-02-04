package com.finalyearapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    int[] idArray = {1,2,3,4,5};
    int[] categoryIdArray = {3,3,4,4,3};
    String[] nameArray = {
            "Top Wear",
            "Bottom Wear",
            "Gaming",
            "Laptop",
            "Footwear"
    };

    ArrayList<SubCategoryList> arrayList;

    RecyclerView recyclerView;
    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_category);
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

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryId INTEGER PRIMARY KEY AUTOINCREMENT,categoryId INTEGER(10),subcategory_name VARCHAR(100))";
        db.execSQL(subcategoryTable);


        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.sub_category_recycler);

        //recyclerView.setLayoutManager(new LinearLayoutManager(CategoryRecyclerActivity.this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        for(int i=0; i<nameArray.length; i++){
            String checkCategory = "SELECT * FROM subcategory WHERE subcategory_name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkCategory, null);

            if(cursor.getCount()>0){
                // Category Already Exists
            }
            else{
                String insertCategory = "INSERT INTO subcategory VALUES (null, '"+categoryIdArray[i]+"', '"+nameArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }

        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            if(sp.getInt(ConstantSp.CATEGORYID,0)==categoryIdArray[i]) {
                SubCategoryList list = new SubCategoryList();
                list.setSubCategoryId(idArray[i]);
                list.setCategoryId(categoryIdArray[i]);
                list.setName(nameArray[i]);
                arrayList.add(list);
            }
        }
        //CategoryAdapter adapter = new CategoryAdapter(CategoryActivity.this,nameArray,imageArray);
        SubCategoryAdapter adapter = new SubCategoryAdapter(SubCategoryActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}