package com.finalyearapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    Context context;
    ArrayList<ProductList> arrayList;
    SharedPreferences sp;
    SQLiteDatabase db;

    boolean isWishlist = false;

    public ProductAdapter(Context context, ArrayList<ProductList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;
        sp= context.getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product,parent,false);
        return new ProductAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView vendorName,name,afterDiscount,price,discount;
        ImageView imageView, wishlist;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            vendorName = itemView.findViewById(R.id.custom_product_vendor_name);
            name = itemView.findViewById(R.id.custom_product_name);
            afterDiscount = itemView.findViewById(R.id.custom_product_after_discount_price);
            price = itemView.findViewById(R.id.custom_product_price);
            discount = itemView.findViewById(R.id.custom_product_discount);
            imageView = itemView.findViewById(R.id.custom_product_image);
            wishlist = itemView.findViewById(R.id.custom_product_wishlist);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyHolder holder, int position) {
        holder.vendorName.setText(arrayList.get(position).getVendorName());
        holder.name.setText(arrayList.get(position).getName());
        holder.afterDiscount.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getAfterDiscount());
        holder.price.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getPrice());
        holder.discount.setText(arrayList.get(position).getDiscount()+"% off");

        Glide.with(context).load(arrayList.get(position).getImage()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);

        holder.price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, arrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                sp.edit().putInt(ConstantSp.PRODUCTID,arrayList.get(position).getProductId()).commit();
                sp.edit().putString(ConstantSp.PRODUCTVENDORNAME,arrayList.get(position).getVendorName()).commit();
                sp.edit().putString(ConstantSp.PRODUCTNAME,arrayList.get(position).getName()).commit();
                sp.edit().putString(ConstantSp.PRODUCTAFTERDISCOUNT,arrayList.get(position).getAfterDiscount()).commit();
                sp.edit().putString(ConstantSp.PRODUCTPRICE,arrayList.get(position).getPrice()).commit();
                sp.edit().putString(ConstantSp.PRODUCTDISCOUNT,arrayList.get(position).getDiscount()).commit();
                sp.edit().putString(ConstantSp.PRODUCTIMAGE,arrayList.get(position).getImage()).commit();

                Intent intent = new Intent(context, ProductDetailActivity.class);
                context.startActivity(intent);
            }
        });

        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWishlist){
                    isWishlist = false;
                    holder.wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(context, "Removed from Wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    isWishlist = true;
                    holder.wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}