package com.finalyearapp;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ProductDetailActivity extends AppCompatActivity {

    TextView vendorName,name,afterDiscount,price,discount, cartQty;
    ImageView imageView, cart, plus, minus;
    LinearLayout cart_layout;

    SharedPreferences sp;

    int qty = 0;

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

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

        String cartTable = "CREATE TABLE IF NOT EXISTS cart(cartId INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(20), productId VARCHAR(20), qty INTEGER(10))";
        db.execSQL(cartTable);


        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        vendorName = findViewById(R.id.product_detail_vendor_name);
        name = findViewById(R.id.product_detail_name);
        afterDiscount = findViewById(R.id.product_detail_after_discount_price);
        price = findViewById(R.id.product_detail_price);
        discount = findViewById(R.id.product_detail_discount);
        imageView = findViewById(R.id.product_detail_image);
        cart = findViewById(R.id.product_detail_cart);
        cart_layout = findViewById(R.id.product_detail_cart_layout);
        plus = findViewById(R.id.product_detail_cart_plus);
        minus = findViewById(R.id.product_detail_cart_minus);
        cartQty = findViewById(R.id.product_detail_cart_qty);






        vendorName.setText(sp.getString(ConstantSp.PRODUCTVENDORNAME,""));
        name.setText(sp.getString(ConstantSp.PRODUCTNAME,""));
        afterDiscount.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTAFTERDISCOUNT,""));
        price.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTPRICE,""));
        discount.setText(sp.getString(ConstantSp.PRODUCTDISCOUNT,"")+"% off");

        Glide.with(ProductDetailActivity.this).load(sp.getString(ConstantSp.PRODUCTIMAGE,"")).placeholder(R.mipmap.ic_launcher).into(imageView);

        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);



        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty = 1;
                String insertItem = "INSERT INTO cart VALUES (NULL, '"+sp.getString(ConstantSp.USERID,"")+"', '"+sp.getString(ConstantSp.PRODUCTID,"")+"', '"+(qty)+"')";
                db.execSQL(insertItem);
                Toast.makeText(ProductDetailActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                cart.setVisibility(View.GONE);
                cart_layout.setVisibility(View.VISIBLE);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                String updateCart = "UPDATE cart SET qty = '"+qty+"' " +
                        "WHERE productId = '"+sp.getString(ConstantSp.PRODUCTID,"")+"' " +
                        "AND userId = '"+sp.getString(ConstantSp.USERID,"")+"'";
                db.execSQL(updateCart);

                cartQty.setText(String.valueOf(qty));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty--;
                if(qty==0){

                    String deleteItem = "DELETE FROM cart WHERE productId = '"+sp.getString(ConstantSp.PRODUCTID,"")+"' " +
                            "AND userId = '"+sp.getString(ConstantSp.USERID,"")+"'";
                    db.execSQL(deleteItem);

                    cart.setVisibility(View.VISIBLE);
                    cart_layout.setVisibility(View.GONE);
                }
                else {
                    String updateCart = "UPDATE cart SET qty = '"+qty+"' " +
                            "WHERE productId = '"+sp.getString(ConstantSp.PRODUCTID,"")+"' " +
                            "AND userId = '"+sp.getString(ConstantSp.USERID,"")+"'";
                    db.execSQL(updateCart);

                    cartQty.setText(String.valueOf(qty));
                }
            }
        });
    }
}