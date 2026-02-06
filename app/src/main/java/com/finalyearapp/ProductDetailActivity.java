package com.finalyearapp;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);


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
                cart.setVisibility(View.GONE);
                cart_layout.setVisibility(View.VISIBLE);
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                cartQty.setText(String.valueOf(qty));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty--;
                if(qty==0){
                    cart.setVisibility(View.VISIBLE);
                    cart_layout.setVisibility(View.GONE);
                }
                else {
                    cartQty.setText(String.valueOf(qty));
                }
            }
        });
        
    }
}