package com.finalyearapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {
    Context context;
    ArrayList<CartList> arrayList;
    SQLiteDatabase db;

    SharedPreferences sp;

    int iCartId, iQty;



    public CartAdapter(Context context, ArrayList<CartList> arrayList, SQLiteDatabase db) {
        this.context = context;
        this.arrayList = arrayList;
        this.db = db;

        sp = context.getSharedPreferences(ConstantSp.PREF, MODE_PRIVATE);
    }

    @NonNull
    @Override
    public CartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart,parent,false);
        return new CartAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView cartImage, delete, minus, plus;
        TextView name, oldPrice, newPrice, discount, qty;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.custom_cart_image);
            delete = itemView.findViewById(R.id.custom_cart_delete);
            name = itemView.findViewById(R.id.custom_cart_name);
            oldPrice = itemView.findViewById(R.id.custom_cart_old_price);
            newPrice = itemView.findViewById(R.id.custom_cart_new_price);
            discount = itemView.findViewById(R.id.custom_cart_discount);
            qty = itemView.findViewById(R.id.custom_cart_qty);
            plus = itemView.findViewById(R.id.custom_cart_plus);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyHolder holder, int position) {

        iCartId = arrayList.get(position).getCartId();
        iQty = arrayList.get(position).getQty();

        holder.name.setText(arrayList.get(position).getName());
        holder.oldPrice.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getOldPrice());
        holder.newPrice.setText(ConstantSp.PRICE_SYMBOL + arrayList.get(position).getNewPrice());
        holder.discount.setText(arrayList.get(position).getDiscount() + "% off");
        holder.qty.setText(String.valueOf(arrayList.get(position).getQty()));

        Glide.with(context).load(arrayList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher).into(holder.cartImage);




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteItem = "DELETE FROM cart WHERE cartId='" + arrayList.get(position).getCartId() + "'";
                db.execSQL(deleteItem);
                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                setCartData(position, 0,0);
//                arrayList.remove(position);
//                notifyDataSetChanged();

            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iCartId = arrayList.get(position).getCartId();
                iQty = arrayList.get(position).getQty();

                iQty = iQty + 1;

                String updateCart = "UPDATE cart SET qty = '"+iQty+"' WHERE cartId = '"+iCartId+"'";
                db.execSQL(updateCart);

                setCartData(position, iCartId, iQty);



            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void setCartData(int position, int iCartId, int qty) {
        if(iCartId == 0){
            arrayList.remove(position);
            notifyDataSetChanged();
        }

        else {
            CartList list = new CartList();
            list.setCartId(iCartId);
            list.setQty(qty);
            list.setProductId(arrayList.get(position).getProductId());
            list.setName(arrayList.get(position).getName());
            list.setOldPrice(arrayList.get(position).getOldPrice());
            list.setNewPrice(arrayList.get(position).getNewPrice());
            list.setDiscount(arrayList.get(position).getDiscount());
            list.setImage(arrayList.get(position).getImage());

            arrayList.set(position, list);
            notifyDataSetChanged();
        }
    }
}
