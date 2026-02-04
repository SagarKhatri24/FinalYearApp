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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    int[] productIdArray = {1,2,3};
    int[] subCategoryIdArray= {1,1,2};
    String[] vendorNameArray = {
            "GM TRENDS",
            "TRIPR",
            "MK BROTHERS"
    };
    String[] nameArray = {
            "Men Printed Round Neck Cotton Blend Blue T-Shirt",
            "Men Solid Henley Neck Cotton Blend Black, Beige T-Shirt",
            "Men Printed, Graphic Print Black Track Pants"
    };
    String[] priceArray = {"399","999","1499"};
    String[] afterDiscountArray = {"241","215","205"};
    String[] discountArray = {"39","78","86"};
    String[] imageArray = {
            "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/p/s/r/m-6002-never-royal-blue-m-gm-trends-original-imahj6jgp3ywwwgh.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/t-shirt/y/c/3/l-tblbghn-d213-tripr-original-imahj8x8nae4aazn.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/track-pant/v/b/6/xl-spider-mk-brothers-original-imahj6xuj2ryrver.jpeg?q=70"
    };

    ArrayList<ProductList> arrayList;

    RecyclerView recyclerView;
    SharedPreferences sp;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product);


        db = openOrCreateDatabase("FinalApp.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))";
        db.execSQL(tableQuery);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryId INTEGER PRIMARY KEY AUTOINCREMENT,categoryId INTEGER(10),subcategory_name VARCHAR(100))";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subcategoryId INTEGER(10),vendorName VARCHAR(100), productName VARCHAR(100),price VARCHAR(100)," +
                "afterDiscount VARCHAR(100),discount VARCHAR(5), image VARCHAR)";
        db.execSQL(productTable);



        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        recyclerView = findViewById(R.id.product_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        for(int i=0; i<nameArray.length; i++){
            String checkProduct = "SELECT * FROM product WHERE productName = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkProduct, null);

            if(cursor.getCount()>0){
                // Product Already Exists
            }
            else{
                String insertProduct = "INSERT INTO product VALUES (null, '"+subCategoryIdArray[i]+"', '"+vendorNameArray[i]+"', '"+nameArray[i]+"', '"+priceArray[i]+"', '"+afterDiscountArray[i]+"', '"+discountArray[i]+"','"+imageArray[i]+"')";
                db.execSQL(insertProduct);
            }
        }


        arrayList = new ArrayList<>();
        for(int i=0;i<nameArray.length;i++){
            if(sp.getInt(ConstantSp.SUBCATEGORYID,0)==subCategoryIdArray[i]) {
                ProductList list = new ProductList();
                list.setProductId(productIdArray[i]);
                list.setSubCategoryId(subCategoryIdArray[i]);
                list.setVendorName(vendorNameArray[i]);
                list.setName(nameArray[i]);
                list.setPrice(priceArray[i]);
                list.setAfterDiscount(afterDiscountArray[i]);
                list.setDiscount(discountArray[i]);
                list.setImage(imageArray[i]);
                arrayList.add(list);
            }
        }
        ProductAdapter adapter = new ProductAdapter(ProductActivity.this,arrayList);
        recyclerView.setAdapter(adapter);

    }
}