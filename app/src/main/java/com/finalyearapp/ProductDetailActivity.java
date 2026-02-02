package com.finalyearapp;

import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ProductDetailActivity extends AppCompatActivity {

    TextView vendorName,name,afterDiscount,price,discount;
    ImageView imageView;

    SharedPreferences sp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        vendorName = findViewById(R.id.product_detail_vendor_name);
        name = findViewById(R.id.product_detail_name);
        afterDiscount = findViewById(R.id.product_detail_after_discount_price);
        price = findViewById(R.id.product_detail_price);
        discount = findViewById(R.id.product_detail_discount);
        imageView = findViewById(R.id.product_detail_image);

        vendorName.setText(sp.getString(ConstantSp.PRODUCTVENDORNAME,""));
        name.setText(sp.getString(ConstantSp.PRODUCTNAME,""));
        afterDiscount.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTAFTERDISCOUNT,""));
        price.setText(ConstantSp.PRICE_SYMBOL + sp.getString(ConstantSp.PRODUCTPRICE,""));
        discount.setText(sp.getString(ConstantSp.PRODUCTDISCOUNT,"")+"% off");

        Glide.with(ProductDetailActivity.this).load(sp.getString(ConstantSp.PRODUCTIMAGE,"")).placeholder(R.mipmap.ic_launcher).into(imageView);

        price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        
    }
}