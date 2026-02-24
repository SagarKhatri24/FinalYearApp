package com.finalyearapp;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    TextView vendorName, name, afterDiscount, price, discount, cartQty;
    ImageView imageView, cart, plus, minus, wishlist_empty, wishlist_filled, wishlist;
    LinearLayout cart_layout;

    Button paynow;

    SharedPreferences sp;
    SQLiteDatabase db;

    int qty = 0;
    String userId;
    int productId;

    boolean isWishlist = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        db = openOrCreateDatabase("FinalApp.db", MODE_PRIVATE, null);

        // Create tables (if not exists)
        db.execSQL("CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(100),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(20),GENDER VARCHAR(10),CITY VARCHAR(50))");

        db.execSQL("CREATE TABLE IF NOT EXISTS category(categoryId INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(50),image VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS subcategory(subcategoryId INTEGER PRIMARY KEY AUTOINCREMENT,categoryId INTEGER(10),subcategory_name VARCHAR(100))");

        db.execSQL("CREATE TABLE IF NOT EXISTS product(productId INTEGER PRIMARY KEY AUTOINCREMENT,subcategoryId INTEGER(10),vendorName VARCHAR(100), productName VARCHAR(100),price VARCHAR(100),afterDiscount VARCHAR(100),discount VARCHAR(5), image VARCHAR)");

        db.execSQL("CREATE TABLE IF NOT EXISTS cart(cartId INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(20), productId VARCHAR(20), qty VARCHAR(10))");

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistId INTEGER PRIMARY KEY AUTOINCREMENT, userId VARCHAR(20), productId VARCHAR(20))";
        db.execSQL(wishlistTable);

        sp = getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);

        // Get logged in user and selected product
        userId = sp.getString(ConstantSp.USERID, "");
        productId = sp.getInt(ConstantSp.PRODUCTID, 0);

        // Initialize views
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

        paynow = findViewById(R.id.pay_now);

//        wishlist_empty = findViewById(R.id.product_detail_wishlist_empty);
//        wishlist_filled = findViewById(R.id.product_detail_wishlist_fill);

        wishlist = findViewById(R.id.product_detail_wishlist);

        // Set product details
        vendorName.setText(sp.getString(ConstantSp.PRODUCTVENDORNAME, ""));
        name.setText(sp.getString(ConstantSp.PRODUCTNAME, ""));
        afterDiscount.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTAFTERDISCOUNT, ""));
        price.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTPRICE, ""));
        discount.setText(sp.getString(ConstantSp.PRODUCTDISCOUNT, "") + "% off");

        Glide.with(this)
                .load(sp.getString(ConstantSp.PRODUCTIMAGE, ""))
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);

        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        checkProductInCart();
        checkItemInWishlist();


        cart.setOnClickListener(view -> {

            String checkQuery = "SELECT * FROM cart WHERE userId='"
                    + userId + "' AND productId='" + productId + "'";
            Cursor cursor = db.rawQuery(checkQuery, null);

            if (cursor.getCount() == 0) {
                qty = 1;
                String insertItem = "INSERT INTO cart VALUES (NULL, '"
                        + userId + "', '" + productId + "', '" + qty + "')";
                db.execSQL(insertItem);
            }



            cartQty.setText(String.valueOf(qty));
            cart.setVisibility(GONE);
            cart_layout.setVisibility(View.VISIBLE);

            Toast.makeText(this, "Added To Cart", Toast.LENGTH_SHORT).show();
        });


        plus.setOnClickListener(view -> {
            qty++;

            String updateCart = "UPDATE cart SET qty='" + qty + "' " +
                    "WHERE productId='" + productId + "' " +
                    "AND userId='" + userId + "'";
            db.execSQL(updateCart);

            cartQty.setText(String.valueOf(qty));
        });


        minus.setOnClickListener(view -> {
            qty--;

            if (qty <= 0) {

                String deleteItem = "DELETE FROM cart WHERE productId='"
                        + productId + "' AND userId='" + userId + "'";
                db.execSQL(deleteItem);

                cart.setVisibility(View.VISIBLE);
                cart_layout.setVisibility(GONE);
                qty = 0;

            } else {

                String updateCart = "UPDATE cart SET qty='" + qty + "' " +
                        "WHERE productId='" + productId + "' " +
                        "AND userId='" + userId + "'";
                db.execSQL(updateCart);

                cartQty.setText(String.valueOf(qty));
            }
        });


//        wishlist_empty.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wishlist_empty.setVisibility(GONE);
//                wishlist_filled.setVisibility(VISIBLE);
//                Toast.makeText(ProductDetailActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        wishlist_filled.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                wishlist_filled.setVisibility(GONE);
//                wishlist_empty.setVisibility(VISIBLE);
//                Toast.makeText(ProductDetailActivity.this, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
//            }
//        });


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWishlist){
                    isWishlist = false;
                    wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(ProductDetailActivity.this, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
                    String deleteWishlist = "DELETE FROM wishlist WHERE userId='"
                            + userId + "' AND productId='" + productId + "'";
                    db.execSQL(deleteWishlist);
                }
                else{
                    isWishlist = true;

                    String insertWishlist = "INSERT INTO wishlist VALUES (NULL, '"+userId+"', '"+productId+"')";
                    db.execSQL(insertWishlist);


                    wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(ProductDetailActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });



        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startpayment();
            }
        });


    }



    private void checkItemInWishlist() {
        String checkWishlist = "SELECT * FROM wishlist WHERE userId='"
                + userId + "' AND productId='" + productId + "'";

        Cursor cursor = db.rawQuery(checkWishlist, null);

        if(cursor.getCount() > 0){
            isWishlist = true;
            wishlist.setImageResource(R.drawable.wishlist_fill);
        }
        else{
            isWishlist = false;
            wishlist.setImageResource(R.drawable.wishlist_empty);
        }

    }


    private void checkProductInCart() {

        String checkQuery = "SELECT qty FROM cart WHERE userId='"
                + userId + "' AND productId='" + productId + "'";

        Cursor cursor = db.rawQuery(checkQuery, null);

        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                qty = Integer.parseInt(cursor.getString(0));
                cartQty.setText(String.valueOf(qty));
            }

            cart.setVisibility(GONE);
            cart_layout.setVisibility(View.VISIBLE);

        } else {
            cart.setVisibility(View.VISIBLE);
            cart_layout.setVisibility(GONE);
        }

        cursor.close();
    }




    private void startpayment() {
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_xsiOz9lYtWKHgF");

        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Purchase Deal From " + getResources().getString(R.string.app_name));
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher);
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(sp.getString(ConstantSp.PRODUCTAFTERDISCOUNT,"")) * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "khatrisagar2@gmail.com");
            preFill.put("contact", "7878232386");
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Sucessfull", Toast.LENGTH_SHORT).show();
        Log.d("RESPONSE_SUCCESS","Transaction Id : "+s);

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        Log.d("RESPONSE_SUCCESS","Transaction Id : "+s);

    }


}
