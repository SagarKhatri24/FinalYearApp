package com.finalyearapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyHolder> {

    Context context;
    ArrayList<WishlistList> arrayList;
    SQLiteDatabase db;
    Boolean isWishlist = true;

    public WishlistAdapter(Context context, ArrayList<WishlistList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;
    }

    @NonNull
    @Override
    public WishlistAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wishlist,parent,false);
        return new WishlistAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView productImage, wishlistIcon;
        TextView productName, oldPrice, discountedPrice, discount;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.custom_wishlist_image);
            wishlistIcon = itemView.findViewById(R.id.custom_wishlist_delete);
            productName = itemView.findViewById(R.id.custom_wishlist_name);
            oldPrice = itemView.findViewById(R.id.custom_wishlist_old_price);
            discountedPrice = itemView.findViewById(R.id.custom_wishlist_new_price);
            discount = itemView.findViewById(R.id.custom_wishlist_discount);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.MyHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher).into(holder.productImage);
        holder.productName.setText(arrayList.get(position).getName());
        holder.oldPrice.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getOldPrice());
        holder.discountedPrice.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getNewPrice());
        holder.discount.setText(ConstantSp.PERCENTAGE_SYMBOL + arrayList.get(position).getDiscount());

        holder.wishlistIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWishlist){
                    isWishlist = false;
                    Toast.makeText(context, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                    holder.wishlistIcon.setImageResource(R.drawable.wishlist_empty);
                }
                else{
                    isWishlist = true;
                    Toast.makeText(context, "Added To Wishlist", Toast.LENGTH_SHORT).show();
                    holder.wishlistIcon.setImageResource(R.drawable.wishlist_fill);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
