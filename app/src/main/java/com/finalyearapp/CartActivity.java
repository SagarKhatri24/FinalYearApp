package com.finalyearapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartList> cartArrayList;
    RecyclerView recyclerView;
    SharedPreferences sp;
    SQLiteDatabase db;
    int sum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_recycler);

        db = openOrCreateDatabase("FinalApp.db", MODE_PRIVATE, null);
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

        String cartTable = "CREATE TABLE IF NOT EXISTS cart(cartId INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(20), productId VARCHAR(20), qty INTEGER(10))";
        db.execSQL(cartTable);


        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));


        cartArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM cart WHERE userId='" + sp.getString(ConstantSp.USERID, "") + "' ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.d("CART", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CartList list = new CartList();
                list.setCartId(Integer.parseInt(cursor.getString(0)));
                list.setQty(Integer.parseInt(cursor.getString(3)));

                String selectProductQuery = "SELECT * FROM product WHERE productId='" + cursor.getString(2) + "'";
                Cursor cursorProduct = db.rawQuery(selectProductQuery, null);
                Log.d("CART", String.valueOf(cursorProduct.getCount()));
                if (cursorProduct.getCount() > 0) {
                    while (cursorProduct.moveToNext()) {
                        list.setProductId(cursor.getInt(0));
                        list.setName(cursorProduct.getString(3));
                        list.setOldPrice(cursorProduct.getString(4));
                        list.setNewPrice(cursorProduct.getString(5));
                        list.setDiscount(cursorProduct.getString(6));
                        list.setImage(cursorProduct.getString(7));
                    }
                }
                cartArrayList.add(list);
            }
            CartAdapter adapter = new CartAdapter(CartActivity.this, cartArrayList, db);
            recyclerView.setAdapter(adapter);


        }
    }
}